package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.application.port.out.InteractionRepositoryPort;
import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public boolean isSubscribed(Vibe subscriberVibe, Vibe targetVibe){
        return delegateRepository.findBySubscriberVibeAndTargetVibe(subscriberVibe,targetVibe).isPresent();
    }
}
