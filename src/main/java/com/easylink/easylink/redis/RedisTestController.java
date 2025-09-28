package com.easylink.easylink.redis;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/redis")
public class RedisTestController {

    private final RedisTestService testService;

    @GetMapping("/set")
    public String set(@RequestParam String key, @RequestParam String value, @AuthenticationPrincipal Jwt jwt){
        testService.saveValue(key, value);
        return "Saved: "+key+" = "+value;
    }

    @GetMapping("/get")
    public String get(@RequestParam String key, @AuthenticationPrincipal Jwt jwt){
        return testService.getValue(key);
    }

    @GetMapping("/inc")
    public String increment(@RequestParam String key, @AuthenticationPrincipal Jwt jwt){
        Long newValue = testService.increment(key);
        return "Counter "+key+ " = " + newValue;
    }
}
