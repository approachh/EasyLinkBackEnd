package com.easylink.easylink.vibe_service.infrastructure.repository;


import com.easylink.easylink.vibe_service.domain.model.VibeField;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataVibeFieldRepository extends JpaRepository<VibeField, UUID> {
    @EntityGraph(attributePaths = "fields")
    List<VibeField> findAllByVibeId(UUID id);
}
