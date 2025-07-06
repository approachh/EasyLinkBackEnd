package com.easylink.easylink.vibe_service.web.mapper;

import com.easylink.easylink.vibe_service.application.dto.InteractionDto;
import com.easylink.easylink.vibe_service.web.dto.InteractionResponse;

public class InteractionResponseMapper {
    public static InteractionResponse toInteractionResponse(InteractionDto interactionDto){
        InteractionResponse interactionResponse = new InteractionResponse();
        interactionResponse.setMyVibeId(interactionDto.getMyVibeId());
        interactionResponse.setTargetVibeId(interactionDto.getTargetVibeId());
        interactionResponse.setInteractionType(interactionDto.getInteractionType());
        interactionResponse.setAnonymous(interactionDto.isAnonymous());
        interactionResponse.setUserEmail(interactionDto.getUserEmail());
        interactionResponse.setCreatedAt(interactionDto.getCreatedAt());
        interactionResponse.setActive(interactionDto.isActive());
        return interactionResponse;
    }
}
