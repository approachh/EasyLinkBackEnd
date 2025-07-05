package com.easylink.easylink.vibe_service.application.port.in.vibe;

import com.easylink.easylink.vibe_service.application.dto.VibeDto;

import java.util.List;
import java.util.UUID;

public interface GetVibeUseCase {
    List<VibeDto> findAllByVibeAccountId(UUID accountId);
    List<VibeDto> findAllByUsername(String user);
    List<VibeDto> findAllById(UUID id);
    List<VibeDto> findAllByAccountId(UUID id);
}
