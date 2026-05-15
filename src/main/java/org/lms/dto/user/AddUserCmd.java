package org.lms.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class AddUserCmd {
    @NotEmpty
    @Length(min =1, max = 30)
    private String userName;

    @Length(max = 20)
    private String phone;

    @Length(max = 50)
    private String email;

    @Length(max = 30)
    private String personName;

    @NotEmpty
    @Length(max = 30)
    private String password;
}
