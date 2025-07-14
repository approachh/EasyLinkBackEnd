package com.easylink.easylink.vibe_service.application.port.in.field;

import com.easylink.easylink.vibe_service.web.dto.VibeFieldDTO;

public interface CreateVibeFieldUseCase {
    VibeFieldDTO create(VibeFieldDTO request);
}
