package com.easylink.easylink.vibe_service.application.port.in.field;

import com.easylink.easylink.vibe_service.web.dto.VibeFieldDTO;

import java.util.List;
import java.util.UUID;

public interface GetVibeFieldUseCase {
    VibeFieldDTO getById(UUID id);
    List<VibeFieldDTO> getAllByVibeId(UUID vibeId);
}
