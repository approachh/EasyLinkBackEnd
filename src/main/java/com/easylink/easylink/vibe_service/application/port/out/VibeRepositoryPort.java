package com.easylink.easylink.vibe_service.application.port.out;

import com.easylink.easylink.vibe_service.domain.model.Vibe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VibeRepositoryPort {
    Vibe save(Vibe vibe);
    Optional<Vibe> findById(UUID id);
    void delete(Vibe vibe);
    List<Vibe> findAllByAccountId(UUID id);
}
