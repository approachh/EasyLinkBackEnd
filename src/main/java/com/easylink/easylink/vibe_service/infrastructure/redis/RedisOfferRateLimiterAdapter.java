package com.easylink.easylink.vibe_service.infrastructure.redis;

import com.easylink.easylink.vibe_service.application.port.in.offer.OfferRateLimitPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisOfferRateLimiterAdapter implements OfferRateLimitPort {

    private final StringRedisTemplate redisTemplate;
    private static final int MAX_OFFERS = 5;

    public boolean canCreateOffer(String userId){
        String key = "limit:offers:"+userId;
        Long count = redisTemplate.opsForValue().increment(key);
        if(count==1){
            redisTemplate.expire(key,Duration.ofDays(365));
        }
        return count <= MAX_OFFERS;
    }

    public void decrementOffer(String userId){
        String key = "limit:offers:"+userId;
        redisTemplate.opsForValue().decrement(key);
    }
}
