package com.easylink.easylink.vibe_service.application.dto;

import com.easylink.easylink.vibe_service.domain.interaction.InteractionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class CreateInteractionCommand {
    private UUID targetVibeId;
    private UUID myVibeId;
    private String userEmail;
    private boolean anonymous;
    private boolean active;
    private InteractionType interactionType;
}
