package org.lms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.lms.app.UserApplication;
import org.lms.dto.AddUserCmd;
import org.lms.dto.LoginDTO;
import org.lms.dto.SsoInfo;
import org.lms.entity.UserEntity;
import org.lms.util.ServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserApplication userApplication;

    @PostMapping(value = "/add")
    public ServiceData<String> addUser(@RequestBody AddUserCmd addUserCmd) {
        String userId = userApplication.addUser(addUserCmd);

        return ServiceData.success(userId);
    }

    @GetMapping(value = "/getDetail/{userId}")
    public ServiceData<UserEntity> getUser(@PathVariable("userId") String userId) {
        UserEntity user = userApplication.getUserById(userId);

        return ServiceData.success(user);
    }

    @PostMapping(value = "/pageQuery")
    public ServiceData pageQuery() {
        IPage<UserEntity> page = userApplication.pageQueryUser();

        return ServiceData.success(page);
    }

    @PostMapping(value = "/login")
    public ServiceData<SsoInfo> login(@RequestBody LoginDTO loginDTO) {
        SsoInfo ssoInfo = userApplication.login(loginDTO);

        return ServiceData.success(ssoInfo);
    }

}
