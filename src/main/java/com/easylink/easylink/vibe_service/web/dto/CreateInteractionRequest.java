package com.easylink.easylink.vibe_service.web.dto;

import com.easylink.easylink.vibe_service.domain.interaction.InteractionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateInteractionRequest {
    private UUID targetVibeId;
    private UUID myVibeId;
    private String userEmail;
    private boolean anonymous;
    private boolean active;
    private InteractionType interactionType;
}
