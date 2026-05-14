package org.lms.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddUserCmd {
    @NotEmpty
    private String userName;
    private String phone;
    private String email;
    private String personName;
    @NotEmpty
    private String password;
}
