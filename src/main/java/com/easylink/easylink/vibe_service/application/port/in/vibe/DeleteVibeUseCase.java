package com.easylink.easylink.vibe_service.application.port.in.vibe;

import java.util.UUID;

public interface DeleteVibeUseCase {
    void delete(UUID id, UUID accountId);
}
