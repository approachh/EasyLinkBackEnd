package com.easylink.easylink.review_service.infrastructure.redis;

import com.easylink.easylink.review_service.application.port.RateLimitPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RedisRateLimiterAdapter implements RateLimitPort {

    private final StringRedisTemplate redisTemplate;

    public boolean canCreateReview(String userId){
        String key = "limit:reviews:"+userId+":"+ LocalDate.now();
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key,"1",Duration.ofDays(1));
        return Boolean.TRUE.equals(success);
    }
}
