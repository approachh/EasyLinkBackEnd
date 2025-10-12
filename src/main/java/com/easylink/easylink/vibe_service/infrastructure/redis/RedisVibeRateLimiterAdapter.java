package com.easylink.easylink.vibe_service.infrastructure.redis;

import com.easylink.easylink.vibe_service.application.port.in.vibe.VibeRateLimitPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
@Component
@RequiredArgsConstructor
public class RedisVibeRateLimiterAdapter implements VibeRateLimitPort {

    private static final int MAX_VIBES = 3;
    private static final Duration TTL = Duration.ofDays(365);
    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean canCreateVibe(String userId, long existingVibeCountFromDb) {
        String key = "limit:vibes:" + userId;

        String value = redisTemplate.opsForValue().get(key);
        long count;

        if (value == null) {
            count = existingVibeCountFromDb;
            redisTemplate.opsForValue().set(key, String.valueOf(count), TTL);
        } else {
            try {
                count = Long.parseLong(value);
            } catch (NumberFormatException e) {
                count = existingVibeCountFromDb;
                redisTemplate.opsForValue().set(key, String.valueOf(count), TTL);
            }
        }

        if (count >= MAX_VIBES) {
            return false;
        }

        redisTemplate.opsForValue().increment(key);
        return true;
    }

    @Override
    public void decrementVibe(String userId) {
        String key = "limit:vibes:" + userId;
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            long count = Long.parseLong(value);
            if (count > 0) {
                redisTemplate.opsForValue().decrement(key);
            }
        }
    }
}

