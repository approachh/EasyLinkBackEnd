package com.easylink.easylink.vibe_service.web.dto;

import com.easylink.easylink.vibe_service.domain.interaction.InteractionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class InteractionResponse {
    private UUID id;
    private UUID vibeId;
    private String userEmail;
    private boolean anonymous;
    private InteractionType interactionType;
    private LocalDateTime createdAt;
    private boolean active;
}
