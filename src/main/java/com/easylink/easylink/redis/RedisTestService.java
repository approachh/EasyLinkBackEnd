package com.easylink.easylink.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTestService {

    private final StringRedisTemplate redisTemplate;

    public void saveValue(String key, String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public String getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Long increment(String key){
        return redisTemplate.opsForValue().increment(key);
    }
}
