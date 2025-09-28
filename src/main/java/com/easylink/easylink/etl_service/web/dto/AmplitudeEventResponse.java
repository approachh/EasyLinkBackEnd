package com.easylink.easylink.etl_service.web.dto;
import com.easylink.easylink.vibe_service.domain.model.AmplitudeEvent;

import java.time.Instant;

public record AmplitudeEventResponse(
        String userId,
        String eventType,
        String insertId,
        Instant serverUploadTime,
        String userProperties) {
    public static AmplitudeEventResponse from(AmplitudeEvent event) {
        return new AmplitudeEventResponse(
                event.getUserId(),
                event.getEventType(),
                event.getInsertId(),
                event.getServerUploadTime(),
                event.getUserProperties()
        );
    }
}
