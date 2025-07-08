package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.dto.InteractionDto;
import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.application.mapper.InteractionDtoMapper;
import com.easylink.easylink.vibe_service.application.mapper.VibeDtoMapper;
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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InteractionService implements CreateInteractionUseCase, DeactivateInteractionUseCase, GetInteractionsByVibeUseCase {

    private final SpringDataVibeRepository springDataVibeRepository;
    private final JpaInteractionRepositoryAdapter interactionRepositoryAdapter;

    @Override
    public InteractionResponse createInteraction(CreateInteractionRequest createInteractionRequest) {
        Interaction interaction = new Interaction();
        Optional<Vibe> vibeOptional = springDataVibeRepository.findById(createInteractionRequest.getTargetVibeId());

        if(vibeOptional.isPresent()){
            interaction.setTargetVibe(vibeOptional.get());
        }else{
            throw new RuntimeException("The target Vibe is not found!");
        }

        Optional<Vibe> myVibeOptional = springDataVibeRepository.findById(createInteractionRequest.getMyVibeId());

        if(myVibeOptional.isPresent()){
            interaction.setSubscriberVibe(myVibeOptional.get());
        }else{
            throw new RuntimeException("The subscriber Vibe is not found!");
        }

        interaction.setAnonymous(createInteractionRequest.isAnonymous());
        interaction.setActive(createInteractionRequest.isActive());
        interaction.setInteractionType(createInteractionRequest.getInteractionType());
        interactionRepositoryAdapter.save(interaction);

        return InteractionResponseMapper.toInteractionResponse(InteractionDtoMapper.toInteractionDto(interaction));
    }

    public List<VibeDto> getFollowing(UUID vibeId){

        Optional<Vibe> vibe = springDataVibeRepository.findById(vibeId);

        List<Interaction> interactions = interactionRepositoryAdapter.getAllFollowings(vibe.get());

        List<Vibe> vibeList = interactions.stream().map(Interaction::getTargetVibe).collect(Collectors.toList());
        List<VibeDto> vibeDtoList = vibeList.stream().map(VibeDtoMapper::toDto).toList();

        return vibeDtoList;
    }

    public Boolean isSubscribed(UUID subscriberVibeId,UUID targetVibeId){

        Vibe subscriberVibe = springDataVibeRepository.findById(subscriberVibeId).orElseThrow(()->new RuntimeException("Subscriber Vibe not found"));
        Vibe targetVibe = springDataVibeRepository.findByTargetVibeId(targetVibeId).orElseThrow(()->new RuntimeException("Target Vibe not found"));

        boolean isSubscribed = interactionRepositoryAdapter.isSubscribed(subscriberVibe,targetVibe);

        return isSubscribed;
    }
}
