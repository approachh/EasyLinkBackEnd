package com.easylink.easylink.vibe_service.infrastructure.repository;


import com.easylink.easylink.vibe_service.domain.interaction.offer.Offer;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataOffer extends JpaRepository<Offer, UUID> {
    List<Offer> findAllByVibe(Vibe vibe);
}
