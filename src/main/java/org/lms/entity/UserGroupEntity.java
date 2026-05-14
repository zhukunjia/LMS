package org.lms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lms_user_group")
public class UserGroupEntity {
    private String id;
    private String userId;
    private String groupCode;
}
