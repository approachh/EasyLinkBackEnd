package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.application.port.out.InteractionRepositoryPort;
import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import org.springframework.stereotype.Repository;

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
}
