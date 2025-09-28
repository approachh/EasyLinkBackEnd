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

    @GetMapping("/events")
    public List<AmplitudeEventResponse> getEvents(
            @RequestParam String type,
            @RequestParam String id,
            @RequestParam (required = false)Instant start,
            @RequestParam (required = false) Instant end){

        return switch(type.toLowerCase()){
            case "user" ->amplitudeEventQueryService.getEventsByUser(id,start,end);
            case "offer" ->amplitudeEventQueryService.getEventsByOffer(id,start,end);
            //  case "catalog" ->amplitudeEventQueryService.getEventsByCatalog(id,start,end);
            default -> throw new IllegalArgumentException("Unsupported type "+type);
        };
    }
}
