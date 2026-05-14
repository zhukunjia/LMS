package org.lms.app;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lms.dto.user.AddUserCmd;
import org.lms.dto.user.LoginDTO;
import org.lms.dto.user.SsoInfo;
import org.lms.entity.UserEntity;
import org.lms.exception.LmsException;
import org.lms.service.UserService;
import org.lms.util.IdGenUtil;
import org.lms.util.RequestContextHolder;
import org.lms.util.RetCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserApplication {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenApplication tokenApplication;

    public String addUser(AddUserCmd addUserCmd) {
        // TODO 最好校验用户名是否已经存在。虽然sql 是已经限制了唯一索引

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

    public UserEntity getUserById(String id) {
        return userService.getById(id);
    }

    public UserEntity getUserByUserName(String userName) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUserName, userName)
                .last(" limit 1");

        return userService.getOne(queryWrapper);
    }

    public IPage<UserEntity> pageQueryUser() {
        IPage<UserEntity> page = new Page<>(1, 10);
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();


        return userService.page(page, queryWrapper);
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
        tokenApplication.removeToken(RequestContextHolder.getUserId());
    }

}
