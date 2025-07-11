package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.application.dto.InteractionWithOffersDTO;
import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataInteraction extends JpaRepository<Interaction, UUID> {
    List<Interaction> findAllBySubscriberVibe(Vibe subscriberVibe);
    Optional<Interaction> findBySubscriberVibeAndTargetVibe(Vibe subscriberVibe, Vibe targetVibe);


    @Query("""
    SELECT i, COUNT(o)
    FROM Interaction i
    LEFT JOIN Offer o ON o.vibe = i.targetVibe AND o.endTime >= CURRENT_TIMESTAMP
    WHERE i.subscriberVibe = :subscriberVibe
    GROUP BY i
""")
    List<Object[]> findAllBySubscriberVibeWithOffers(@Param("subscriberVibe") Vibe subscriberVibe);
}
