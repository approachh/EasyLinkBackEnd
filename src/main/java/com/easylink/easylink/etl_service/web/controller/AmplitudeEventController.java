package com.easylink.easylink.etl_service.web.controller;

import com.easylink.easylink.etl_service.application.service.AmplitudeEventQueryService;
import com.easylink.easylink.etl_service.web.dto.AmplitudeEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/analytics")
public class AmplitudeEventController {

    private final AmplitudeEventQueryService amplitudeEventQueryService;

    @GetMapping("/events")
    public List<AmplitudeEventResponse> getEvents(
            @RequestParam String type,
            @RequestParam String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {

        return switch (type.toLowerCase()) {
            case "user" -> amplitudeEventQueryService.getEventsByUser(id, start, end);
            case "offer" -> amplitudeEventQueryService.getEventsByOffer(id, start, end);
            default -> throw new IllegalArgumentException("Unsupported type " + type);
        };
    }
}
