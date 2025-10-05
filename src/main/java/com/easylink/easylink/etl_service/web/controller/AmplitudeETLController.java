package com.easylink.easylink.etl_service.web.controller;

import com.easylink.easylink.etl_service.application.service.AmplitudeETLService;
import com.easylink.easylink.etl_service.application.service.AmplitudeEventQueryService;
import com.easylink.easylink.etl_service.web.dto.AmplitudeEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v3/etl")
@RequiredArgsConstructor
public class AmplitudeETLController {

    private final AmplitudeETLService etlService;
    private final AmplitudeEventQueryService amplitudeEventQueryService;

    @GetMapping("/run")
    public String runETL(@RequestParam String start, @RequestParam String end, @AuthenticationPrincipal Jwt jwt) {
        etlService.fetchAndSave(start, end);
        return "ETL started for " + start + " → " + end;
    }
}
