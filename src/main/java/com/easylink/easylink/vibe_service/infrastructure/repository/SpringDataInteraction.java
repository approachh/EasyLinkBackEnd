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


    @Query(value = """
    WITH tableInt AS (
        SELECT id,subscriber_vibe_id, target_vibe_id,created_at
        FROM interaction 
        WHERE subscriber_vibe_id = :#{#subscriberVibe.id}
    ),
    offers_count AS (
        SELECT 
            i.id AS interaction_id,
            i.subscriber_vibe_id AS subscriber_vibe_id,
            i.target_vibe_id AS target_vibe_id,
            i.created_at AS created_at,
            COUNT(o.id) AS active_offer_count
        FROM interaction i
        LEFT JOIN offer o 
            ON i.target_vibe_id = o.vibe_id
            AND o.end_time >= NOW()
        WHERE i.subscriber_vibe_id = :#{#subscriberVibe.id}
        GROUP BY i.id
    )
    SELECT 
        t.*, 
        oc.active_offer_count
    FROM tableInt t
    LEFT JOIN offers_count oc ON t.id = oc.interaction_id
    """, nativeQuery = true)
    List<Object[]> findAllBySubscriberVibeWithOffers(@Param("subscriberVibe") Vibe subscriberVibe);
}
