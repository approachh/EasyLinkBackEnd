package com.easylink.easylink.vibe_service.application.port.in;

import com.easylink.easylink.vibe_service.application.dto.CreateVibeCommand;
import com.easylink.easylink.vibe_service.application.dto.VibeDto;

public interface CreateVibeUseCase {
    VibeDto create(CreateVibeCommand command, String vibeAccountId);
}
