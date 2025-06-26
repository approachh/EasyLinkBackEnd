package com.easylink.easylink.vibe_service.application.port.in;

import com.easylink.easylink.vibe_service.application.dto.VibeDto;

import java.util.List;
import java.util.UUID;

public interface GetVibeUseCase {
    List<VibeDto> findAllByVibeAccountId(UUID accountId);
}
