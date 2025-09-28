package com.easylink.easylink.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private StringRedisTemplate redisTemplate;

    private static final int MAX_VIBES = 3;
    private static  final int MAX_OFFERS = 5;

    public boolean canCreateVibe(String userId){
        String key = "limit:vibes: "+userId;
        Long count = redisTemplate.opsForValue().increment(key);
        if(count==1){
            redisTemplate.expire(key, Duration.ofDays(365));
        }
        return count <=MAX_VIBES;
    }

    public boolean canCreateOffer(String userId){
        String key = "limit:offers:"+userId;
        Long count = redisTemplate.opsForValue().increment(key);
        if(count==1){
            redisTemplate.expire(key,Duration.ofDays(365));
        }
        return count <= MAX_OFFERS;
    }

    public boolean canCreateReview(String userId){
        String key = "limit:reviews:"+userId+":"+ LocalDate.now();
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key,"1",Duration.ofDays(1));
        return Boolean.TRUE.equals(success);
    }

    public void decrementVibe(String userId){
        String key = "limit:vibes:"+userId;
        redisTemplate.opsForValue().decrement(key);
    }

    public void decrementOffer(String userId){
        String key = "limit:offers:"+userId;
        redisTemplate.opsForValue().decrement(key);
    }

}
