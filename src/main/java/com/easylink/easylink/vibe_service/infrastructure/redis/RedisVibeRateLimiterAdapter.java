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
    private final StringRedisTemplate redisTemplate;

    public boolean canCreateVibe(String userId) {
        String key = "limit:vibes: " + userId;
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == 1) {
            redisTemplate.expire(key, Duration.ofDays(365));
        }
        return count <= MAX_VIBES;
    }

    public void decrementVibe(String userId) {
        String key = "limit:vibes:" + userId;
        redisTemplate.opsForValue().decrement(key);
    }
}
