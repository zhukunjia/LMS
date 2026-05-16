package org.lms.dto.user;

import lombok.Data;

@Data
public class UserQuery {
    private Long pageSize = 10L;
    private Long current = 1L;
    private String userName;
}
