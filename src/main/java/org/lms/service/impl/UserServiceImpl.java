package org.lms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lms.entity.UserEntity;
import org.lms.mapper.UserMapper;
import org.lms.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
}
