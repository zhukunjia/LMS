package org.lms.app;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *
 */
@Service
public class TokenApplication {

    private final Cache<String, String> tokenCache = Caffeine.newBuilder()
            .expireAfterWrite(8, TimeUnit.HOURS)
            .build();

    public String getToken(String userId) {

        return tokenCache.getIfPresent(userId);
    }

    public void putToken(String userId, String token) {
        tokenCache.put(userId, token);
    }

    public boolean checkToken(String userId, String token) {
        String cacheToken = tokenCache.getIfPresent(userId);
        if(StringUtils.isEmpty(cacheToken)) {
            return false;
        }

        return StringUtils.equals(cacheToken, token);
    }

    public void removeToken(String userId) {
        tokenCache.invalidate(userId);
    }

}
