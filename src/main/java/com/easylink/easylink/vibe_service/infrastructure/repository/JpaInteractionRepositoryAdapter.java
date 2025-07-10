package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.application.dto.InteractionWithOffersDTO;
import com.easylink.easylink.vibe_service.application.port.out.InteractionRepositoryPort;
import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaInteractionRepositoryAdapter implements InteractionRepositoryPort {

    SpringDataInteraction delegateRepository;

    public JpaInteractionRepositoryAdapter(SpringDataInteraction springDataInteraction){
        this.delegateRepository = springDataInteraction;
    }

    @Override
    public Interaction save(Interaction interaction) {
        return delegateRepository.save(interaction);
    }

    @Override
    public List<Interaction> getAllFollowings(Vibe subscriberVibe) {
        List<Interaction> interactionList = delegateRepository.findAllBySubscriberVibe(subscriberVibe);
        return interactionList;
    }

    @Override
    public List<InteractionWithOffersDTO> getAllFollowingsWithOffers(Vibe subscriberVibe) {
        List<Object[]> rows = delegateRepository.findAllBySubscriberVibeWithOffers(subscriberVibe);

        return rows.stream()
                .map(row -> {
                    // Проверяем, что row[3] не null и корректно приводим к LocalDateTime
                    LocalDateTime createdAt = null;
                    if (row[3] != null) {
                        Object raw = row[3];
                        if (raw instanceof Timestamp ts) {
                            createdAt = ts.toLocalDateTime();
                        } else if (raw instanceof Date date) {
                            createdAt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        } else if (raw instanceof String str) {
                            createdAt = LocalDateTime.parse(str);
                        } else {
                            throw new IllegalArgumentException("Unknown date type: " + raw.getClass());
                        }
                    }
                    // Если row[4] null, считаем количество офферов 0
                    int offerCount = row[4] == null ? 0 : ((Number) row[4]).intValue();

                    return new InteractionWithOffersDTO(
                            UUID.fromString(row[0].toString()),
                            UUID.fromString(row[1].toString()),
                            UUID.fromString(row[2].toString()),
                            createdAt,
                            offerCount
                    );
                })
                .toList();
    }


    public boolean isSubscribed(Vibe subscriberVibe, Vibe targetVibe){
        return delegateRepository.findBySubscriberVibeAndTargetVibe(subscriberVibe,targetVibe).isPresent();
    }
}
