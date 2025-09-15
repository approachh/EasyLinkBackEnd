package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.domain.model.AmplitudeEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringAmplitudeEventRepository extends JpaRepository<AmplitudeEvent, UUID> {



}
