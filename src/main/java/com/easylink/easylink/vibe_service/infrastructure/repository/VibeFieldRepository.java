package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.domain.model.VibeField;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface VibeFieldRepository extends JpaRepository<VibeField, UUID> {
    // Получить все поля для определённого вайба
    List<VibeField> findAllByVibe_Id(UUID vibeId);
}
