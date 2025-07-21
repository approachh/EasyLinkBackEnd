package com.easylink.easylink.vibe_service.application.dto;

import com.easylink.easylink.vibe_service.domain.interaction.Interaction;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public record InteractionWithOffersDTO(Interaction interaction, int count) {}
