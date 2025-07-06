package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.dto.InteractionDto;
import com.easylink.easylink.vibe_service.application.mapper.InteractionDtoMapper;
import com.easylink.easylink.vibe_service.application.port.in.interaction.CreateInteractionUseCase;
import com.easylink.easylink.vibe_service.application.port.in.interaction.DeactivateInteractionUseCase;
import com.easylink.easylink.vibe_service.application.port.in.interaction.GetInteractionsByVibeUseCase;
import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import com.easylink.easylink.vibe_service.infrastructure.repository.JpaInteractionRepositoryAdapter;
import com.easylink.easylink.vibe_service.infrastructure.repository.SpringDataVibeRepository;
import com.easylink.easylink.vibe_service.web.dto.CreateInteractionRequest;
import com.easylink.easylink.vibe_service.web.dto.InteractionResponse;
import com.easylink.easylink.vibe_service.web.mapper.InteractionResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InteractionService implements CreateInteractionUseCase, DeactivateInteractionUseCase, GetInteractionsByVibeUseCase {

    SpringDataVibeRepository springDataVibeRepository;
    JpaInteractionRepositoryAdapter interactionRepositoryAdapter;

    @Override
    public InteractionResponse createInteraction(CreateInteractionRequest createInteractionRequest) {
        Interaction interaction = new Interaction();
        Optional<Vibe> vibeOptional = springDataVibeRepository.findById(createInteractionRequest.getTargetVibeId());

        if(vibeOptional.isPresent()){
            interaction.setTargetVibe(vibeOptional.get());
        }else{
            throw new RuntimeException("The target Vibe is not found!");
        }

        interaction.setAnonymous(createInteractionRequest.isAnonymous());
        interaction.setActive(createInteractionRequest.isActive());
        interaction.setInteractionType(createInteractionRequest.getInteractionType());
        interactionRepositoryAdapter.save(interaction);

        return InteractionResponseMapper.toInteractionResponse(InteractionDtoMapper.toInteractionDto(interaction));
    }
}
