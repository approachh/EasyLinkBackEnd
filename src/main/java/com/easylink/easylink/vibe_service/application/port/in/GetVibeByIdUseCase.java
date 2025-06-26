package com.easylink.easylink.vibe_service.application.port.in;

import com.easylink.easylink.vibe_service.application.dto.VibeDto;

import java.util.UUID;

public interface GetVibeByIdUseCase {
    VibeDto getVibeById(UUID id);
}
