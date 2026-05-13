package org.lms.dto;

import lombok.Data;

@Data
public class AddUserCmd {
    private String userName;
    private String phone;
    private String email;
    private String personName;
    private String password;
}
