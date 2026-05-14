package org.lms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lms.entity.UserGroupEntity;

import java.util.Set;

public interface UserGroupService extends IService<UserGroupEntity> {

    Set<String> queryGroupCodeByUserId(String userId);
}
