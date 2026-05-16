package org.lms.app;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lms.converter.UserConverter;
import org.lms.dto.PageDTO;
import org.lms.dto.user.*;
import org.lms.entity.UserEntity;
import org.lms.exception.LmsException;
import org.lms.service.UserService;
import org.lms.util.IdGenUtil;
import org.lms.util.RequestContextHolder;
import org.lms.util.RetCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserApplication {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenApplication tokenApplication;

    public String addUser(AddUserCmd addUserCmd) {
        // 根据 userName 查询用户表，如果存在，返回错误。不存在则继续
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserEntity::getId, UserEntity::getUserName)
                .eq(UserEntity::getUserName, addUserCmd.getUserName())
                .last(" limit 1");
        UserEntity existEntity = userService.getOne(queryWrapper);
        if (null != existEntity) {
            log.info("failed to add user. the userName = {} is exist", addUserCmd.getUserName());

            throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "userName is exist");
        }

        String id = IdGenUtil.genUserId();
        // 密码需要加密存储，不可逆加密
        String hashedPassword = BCrypt.hashpw(addUserCmd.getPassword());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUserName(addUserCmd.getUserName());
        userEntity.setPassword(hashedPassword);
        userEntity.setCreateBy("admin");
        userEntity.setLastModifiedBy("admin");

        boolean save = userService.save(userEntity);
        if (save) {
            log.info("Add user success. userName = {} and id = {} .", addUserCmd.getUserName(), id);
            return id;
        }

        throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "add user failed");
    }

    public UserDTO getUserById() {
        // userId 从请求头拿，避免从url 路径上传进来，会存在越权的风险
        String userId = RequestContextHolder.getUserId();

        UserEntity entity = userService.getById(userId);

        return UserConverter.INSTANCE.entityToDTO(entity);
    }

    public UserEntity getUserByUserName(String userName) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUserName, userName)
                .last(" limit 1");

        return userService.getOne(queryWrapper);
    }

    public PageDTO<UserDTO> pageQueryUser(UserQuery query) {
        IPage<UserEntity> page = new Page<>(query.getCurrent(), query.getPageSize());
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(query.getUserName()), UserEntity::getUserName, query.getUserName());

        IPage<UserEntity> entityIPage = userService.page(page, queryWrapper);
        List<UserDTO> userDTOS = UserConverter.INSTANCE.entityListToDTO(entityIPage.getRecords());

        PageDTO<UserDTO> pageDTO = new PageDTO<>(entityIPage.getCurrent(), entityIPage.getSize(), entityIPage.getTotal());
        pageDTO.setRecords(userDTOS);

        return pageDTO;
    }

    public SsoInfo login(LoginDTO loginDTO) {
        // 校验必填
        if (StringUtils.isEmpty(loginDTO.getUserName()) || StringUtils.isEmpty(loginDTO.getPassword())) {
            log.info("login failed, userName or password is empty");

            throw new LmsException(RetCode.PARAM_ERROR.getCode(), "userName and password is empty");
        }
        UserEntity entity = getUserByUserName(loginDTO.getUserName());
        if (null == entity) {
            log.info("login failed, userName = {} does not exist.", loginDTO.getUserName());
            throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "userName does not exist");
        }

        boolean check = BCrypt.checkpw(loginDTO.getPassword(), entity.getPassword());
        if (check) {
            log.info("userName = {} login success.", loginDTO.getUserName());
            // 这里查询缓存，如果存在直接返回当前token。避免token一直变化
            String token = tokenApplication.getToken(entity.getId());
            if (StringUtils.isEmpty(token)) {
                token = UUID.randomUUID().toString().replace("-", "");
            }
            // 缓存token。以后直接校验token是否存在
            tokenApplication.putToken(entity.getId(), token);

            return new SsoInfo(entity.getId(), token);
        } else {
            log.info("userName = {} login failed, the password is wrong.", loginDTO.getUserName());
            throw new LmsException(RetCode.BUSINESS_ERROR.getCode(), "password is wrong");
        }

    }

    public void logout() {
        String userId = RequestContextHolder.getUserId();
        log.info("the userId = {} logout.", userId);

        tokenApplication.removeToken(userId);
    }

}
