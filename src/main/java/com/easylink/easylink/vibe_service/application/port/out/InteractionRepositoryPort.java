package com.easylink.easylink.vibe_service.application.port.out;

import com.easylink.easylink.vibe_service.domain.interaction.Interaction;

public interface InteractionRepositoryPort {
    Interaction save(Interaction interaction);
}
