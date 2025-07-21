package com.easylink.easylink.vibe_service.application.mapper;

import com.easylink.easylink.vibe_service.application.dto.InteractionDto;
import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import com.easylink.easylink.vibe_service.web.dto.InteractionResponse;

public class InteractionDtoMapper {
    public static InteractionDto toInteractionDto(Interaction interaction){
        InteractionDto interactionDto = new InteractionDto();
        interactionDto.setMyVibeId(interaction.getId());
        interactionDto.setTargetVibeId(interaction.getTargetVibe().getId());
        interactionDto.setInteractionType(interaction.getInteractionType());
        interactionDto.setAnonymous(interaction.isAnonymous());
        interactionDto.setUserEmail(interaction.getUserEmail());
        interactionDto.setCreatedAt(interaction.getCreatedAt());
        interactionDto.setActive(interaction.isActive());
        return interactionDto;
    }
}
