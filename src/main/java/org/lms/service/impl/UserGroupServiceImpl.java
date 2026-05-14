package org.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lms.entity.UserGroupEntity;
import org.lms.mapper.UserGroupMapper;
import org.lms.service.UserGroupService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroupEntity> implements UserGroupService {

    @Override
    public Set<String> queryGroupCodeByUserId(String userId) {
        LambdaQueryWrapper<UserGroupEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserGroupEntity::getUserId, userId);

        List<UserGroupEntity> groupEntityList = list(queryWrapper);
        if(null == groupEntityList || groupEntityList.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> groupCodeSet = groupEntityList.stream()
                .map(UserGroupEntity::getGroupCode)
                .collect(Collectors.toSet());

        return groupCodeSet;
    }
}
