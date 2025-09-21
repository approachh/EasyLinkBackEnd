package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.etl.application.service.AmplitudeETLService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/etl")
@RequiredArgsConstructor
public class AmplitudeETLController {

    private final AmplitudeETLService etlService;

    @GetMapping("/run")
    public String runETL(@RequestParam String start, @RequestParam String end, @AuthenticationPrincipal Jwt jwt) {
        etlService.fetchAndSave(start, end);
        return "ETL started for " + start + " → " + end;
    }
}
