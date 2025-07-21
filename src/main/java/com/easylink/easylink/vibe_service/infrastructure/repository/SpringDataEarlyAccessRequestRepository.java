package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.domain.model.EarlyAccessRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataEarlyAccessRequestRepository extends JpaRepository<EarlyAccessRequest, UUID> {
    EarlyAccessRequest findByEmail(String email);
}
