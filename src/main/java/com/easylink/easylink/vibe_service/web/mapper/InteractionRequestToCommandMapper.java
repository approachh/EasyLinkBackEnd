package com.easylink.easylink.vibe_service.web.mapper;

import com.easylink.easylink.vibe_service.application.dto.CreateInteractionCommand;
import com.easylink.easylink.vibe_service.web.dto.CreateInteractionRequest;

public class InteractionRequestToCommandMapper {
    public static CreateInteractionCommand toCommand(CreateInteractionRequest createInteractionRequest){
        CreateInteractionCommand interactionCommand = new CreateInteractionCommand(createInteractionRequest.getTargetVibeId(),
                createInteractionRequest.getMyVibeId(),
                createInteractionRequest.getUserEmail(),
                createInteractionRequest.isAnonymous(),
                createInteractionRequest.getInteractionType());
        return interactionCommand;
    };
}
