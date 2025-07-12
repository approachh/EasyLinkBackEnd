package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.dto.EarlyAccessRequestDTO;
import com.easylink.easylink.vibe_service.application.dto.InteractionWithOffersDTO;
import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.application.mapper.InteractionDtoMapper;
import com.easylink.easylink.vibe_service.application.mapper.VibeDtoMapper;
import com.easylink.easylink.vibe_service.application.port.in.interaction.*;
import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import com.easylink.easylink.vibe_service.domain.model.EarlyAccessRequest;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import com.easylink.easylink.vibe_service.infrastructure.repository.JpaEarlyAccessRequestAdapter;
import com.easylink.easylink.vibe_service.infrastructure.repository.JpaInteractionRepositoryAdapter;
import com.easylink.easylink.vibe_service.infrastructure.repository.SpringDataVibeRepository;
import com.easylink.easylink.vibe_service.web.dto.CreateInteractionRequest;
import com.easylink.easylink.vibe_service.web.dto.InteractionResponse;
import com.easylink.easylink.vibe_service.web.mapper.InteractionResponseMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InteractionService implements CreateInteractionUseCase, DeactivateInteractionUseCase, GetInteractionsByVibeUseCase, CreateEarlyAccessUseCase, EarlyAccessCheckable {

    private final SpringDataVibeRepository springDataVibeRepository;
    private final JpaInteractionRepositoryAdapter interactionRepositoryAdapter;
    private final JpaEarlyAccessRequestAdapter jpaEarlyAccessRequestAdapter;
    private final ModelMapper modelMapper;

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
    public List<InteractionWithOffersDTO> getFollowingWithOffers(UUID vibeId){

        Optional<Vibe> vibe = springDataVibeRepository.findById(vibeId);

        List<InteractionWithOffersDTO>  interactions = interactionRepositoryAdapter.getAllFollowingsWithOffers(vibe.get());

        return interactions;
    }


    public Boolean isSubscribed(UUID subscriberVibeId,UUID targetVibeId){

        Vibe subscriberVibe = springDataVibeRepository.findById(subscriberVibeId).orElseThrow(()->new RuntimeException("Subscriber Vibe not found"));
        Vibe targetVibe = springDataVibeRepository.findById(targetVibeId).orElseThrow(()->new RuntimeException("Target Vibe not found"));

        boolean isSubscribed = interactionRepositoryAdapter.isSubscribed(subscriberVibe,targetVibe);

        return isSubscribed;
    }

    @Override
    public EarlyAccessRequestDTO create(String email) {

        EarlyAccessRequest earlyAccessRequest = new EarlyAccessRequest();
        earlyAccessRequest.setCreatedAt(Instant.now());
        earlyAccessRequest.setApproved(true);
        earlyAccessRequest.setEmail(email);

        EarlyAccessRequest earlyAccessRequestSaved = jpaEarlyAccessRequestAdapter.save(earlyAccessRequest);

        EarlyAccessRequestDTO earlyAccessRequestDTO = modelMapper.map(earlyAccessRequestSaved,EarlyAccessRequestDTO.class);

        return earlyAccessRequestDTO;
    }

    @Override
    public boolean isSubscribedEarlyAccess(String email) {

        return jpaEarlyAccessRequestAdapter.isSubscribedEarlyAccess(email);

    }
}
