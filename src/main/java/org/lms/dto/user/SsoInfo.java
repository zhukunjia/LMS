package org.lms.dto.user;

import lombok.Data;

@Data
public class SsoInfo {
    private String userId;
    private String token;

    public SsoInfo() {
    }

    public SsoInfo(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}

