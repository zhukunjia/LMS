package org.lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.lms.annotation.Permission;
import org.lms.app.UserApplication;
import org.lms.dto.PageDTO;
import org.lms.dto.user.*;
import org.lms.util.Constant;
import org.lms.util.ServiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "用户管理接口", description = "提供用户新增、查询、登录、登出接口")
public class UserController {

    @Autowired
    private UserApplication userApplication;

    @PostMapping(value = "/add")
    @Operation(summary = "新增用户")
    @Permission(role = {Constant.MANAGE})
    public ServiceData<String> addUser(@RequestBody @Valid AddUserCmd addUserCmd) {
        String userId = userApplication.addUser(addUserCmd);

        return ServiceData.success(userId);
    }

    @GetMapping(value = "/getDetail")
    @Operation(summary = "查询用户详情")
    public ServiceData<UserDTO> getUser() {
        UserDTO user = userApplication.getUserById();

        return ServiceData.success(user);
    }

    @PostMapping(value = "/pageQuery")
    @Operation(summary = "分页查询用户")
    @Permission(role = {Constant.MANAGE})
    public ServiceData<PageDTO<UserDTO>> pageQuery(@RequestBody UserQuery query) {
        PageDTO<UserDTO> page = userApplication.pageQueryUser(query);

        return ServiceData.success(page);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "登录")
    public ServiceData<SsoInfo> login(@RequestBody @Valid LoginDTO loginDTO) {
        SsoInfo ssoInfo = userApplication.login(loginDTO);

        return ServiceData.success(ssoInfo);
    }

    @PostMapping(value = "/logout")
    @Operation(summary = "登出")
    public ServiceData<Void> logout() {
        userApplication.logout();

        return ServiceData.success();
    }

}
