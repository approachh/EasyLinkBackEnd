package com.easylink.easylink.vibe_service.application.port.out;

import com.easylink.easylink.vibe_service.domain.interaction.offer.Offer;
import com.easylink.easylink.vibe_service.domain.model.Vibe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferRepositoryPort {
    Offer save(Offer offer);
    List<Offer> findAllByVibe(Vibe vibe);
    Optional<Offer> findById(UUID id);
}
