package com.easylink.easylink.vibe_service.application.port.in.interaction;

import com.easylink.easylink.vibe_service.application.dto.CreateEarlyAccessRequestCommand;
import com.easylink.easylink.vibe_service.application.dto.EarlyAccessRequestDTO;

import java.util.UUID;

public interface CreateEarlyAccessUseCase {
    EarlyAccessRequestDTO create(String email);
}
