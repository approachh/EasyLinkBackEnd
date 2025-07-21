package com.easylink.easylink.vibe_service.application.port.in.interaction;

import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import com.easylink.easylink.vibe_service.domain.interaction.InteractionType;
import com.easylink.easylink.vibe_service.web.dto.CreateInteractionRequest;
import com.easylink.easylink.vibe_service.web.dto.InteractionResponse;

import java.util.UUID;

public interface CreateInteractionUseCase {
    InteractionResponse createInteraction(CreateInteractionRequest createInteractionRequest);
}
