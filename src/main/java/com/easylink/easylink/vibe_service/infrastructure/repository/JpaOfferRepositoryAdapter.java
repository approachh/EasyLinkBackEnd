package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.application.port.out.OfferRepositoryPort;
import com.easylink.easylink.vibe_service.domain.interaction.offer.Offer;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaOfferRepositoryAdapter implements OfferRepositoryPort {

    private final SpringDataOffer springDataOffer;

    @Override
    public Offer save(Offer offer) {
        Offer offerOptional = springDataOffer.save(offer);
        return offerOptional;
    }

    @Override
    public List<Offer> findAllByVibe(Vibe vibe) {
        List<Offer> offerList = springDataOffer.findAllByVibe(vibe);
        return offerList;
    }
}
