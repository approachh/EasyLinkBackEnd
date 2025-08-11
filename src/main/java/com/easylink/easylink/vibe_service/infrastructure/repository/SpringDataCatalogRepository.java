package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.domain.model.Item;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataCatalogRepository extends JpaRepository<Item, UUID> {
    List<Item> findByVibeId(UUID vibeId);
    Optional<Item> findById(UUID vibeId);
}
