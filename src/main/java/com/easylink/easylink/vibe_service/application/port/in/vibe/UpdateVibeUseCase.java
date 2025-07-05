package com.easylink.easylink.vibe_service.application.port.in.vibe;

import com.easylink.easylink.vibe_service.application.dto.UpdateVibeCommand;
import com.easylink.easylink.vibe_service.application.dto.VibeDto;

public interface UpdateVibeUseCase {
    VibeDto update(UpdateVibeCommand updateVibeCommand);
}
