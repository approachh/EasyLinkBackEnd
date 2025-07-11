package com.easylink.easylink.vibe_service.web.dto;

import com.easylink.easylink.vibe_service.domain.interaction.Interaction;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public record InteractionWithOfferResponse(
        UUID interactionId,
        UUID targetVibeId,
        String targetVibeName,
        String targetVibeType,
        String targetVibeDescription,
        int count
) {}