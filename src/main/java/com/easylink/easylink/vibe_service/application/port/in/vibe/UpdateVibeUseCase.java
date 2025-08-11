package com.easylink.easylink.vibe_service.application.port.in.vibe;

import com.easylink.easylink.vibe_service.application.dto.UpdateVibeCommand;
import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.web.dto.UpdateVibeRequest;

import java.util.UUID;

public interface UpdateVibeUseCase {
    VibeDto update(UpdateVibeRequest updateVibeCommand, UUID vibeId, UUID accountId);
}
