package org.lms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("lms_user")
public class UserEntity {
    private String id;
    private String userName;
    private String phone;
    private String email;
    private String personName;
    private String password;
    private String createBy;
    private Date createTime;
    private String lastModifiedBy;
    private Date lastModifiedTime;
}
