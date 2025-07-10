package com.easylink.easylink.vibe_service.web.controller;

import ch.qos.logback.core.model.processor.ModelFilter;
import com.easylink.easylink.vibe_service.application.dto.InteractionWithOffersDTO;
import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.application.port.in.interaction.CreateInteractionUseCase;
import com.easylink.easylink.vibe_service.application.service.InteractionService;
import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import com.easylink.easylink.vibe_service.infrastructure.repository.SpringDataVibeRepository;
import com.easylink.easylink.vibe_service.web.dto.CreateInteractionRequest;
import com.easylink.easylink.vibe_service.web.dto.InteractionResponse;
import com.easylink.easylink.vibe_service.web.dto.InteractionWithOfferResponse;
import com.easylink.easylink.vibe_service.web.dto.VibeResponse;
import com.easylink.easylink.vibe_service.web.mapper.VibeResponseMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v3/interactions")
@RequiredArgsConstructor
public class InteractionController {

    private final CreateInteractionUseCase createInteractionUseCase;
    private final InteractionService interactionService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<InteractionResponse> create(@RequestBody CreateInteractionRequest createInteractionRequest, @AuthenticationPrincipal Jwt jwt){

        String vibeAccountId = jwt.getSubject();

        InteractionResponse interactionResponse = createInteractionUseCase.createInteraction(createInteractionRequest);

        return ResponseEntity.ok(interactionResponse);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<VibeResponse>> getFollowing(@PathVariable UUID id,@AuthenticationPrincipal Jwt jwt){

        List<VibeDto> vibeDtoList = interactionService.getFollowing(id);
        List<VibeResponse> vibeResponseList = vibeDtoList.stream().map(val->VibeResponseMapper.toResponse(val)).toList();

        return ResponseEntity.ok(vibeResponseList);
    }

    @GetMapping("/{id}/following-offer")
    public ResponseEntity<List<InteractionWithOfferResponse>> getFollowingWithOffers(@PathVariable UUID id,@AuthenticationPrincipal Jwt jwt){

        List<InteractionWithOffersDTO> interactionWithOffersDTO = interactionService.getFollowingWithOffers(id);

        //   List<InteractionWithOfferResponse> interactionWithOfferResponse = interactionWithOffersDTO.stream().map(dto-> modelMapper.map(dto, InteractionWithOfferResponse.class)).toList();
        List<InteractionWithOfferResponse> interactionWithOfferResponse = interactionWithOffersDTO.stream().map(dto->new InteractionWithOfferResponse(
                dto.id(),
                dto.subscriber_vibe_id(),
                dto.target_vibe_id(),
                dto.created_at(),
                dto.active_offer_count())).toList();

        return ResponseEntity.ok(interactionWithOfferResponse);
    }

    @GetMapping("/{subscriberVibeId}/subscribed")
    public ResponseEntity<Boolean> isSubscribed(@PathVariable UUID subscriberVibeId, @RequestParam UUID targetVibeId, @AuthenticationPrincipal Jwt jwt){

        Boolean isSubscribed = interactionService.isSubscribed(subscriberVibeId,targetVibeId);

        return ResponseEntity.ok(isSubscribed);
    }

    @RequestMapping("/followers")
    public ResponseEntity<VibeResponse> getFollowers(@RequestParam UUID vibeId,@AuthenticationPrincipal Jwt jwt){



        return ResponseEntity.ok(null);
    }

}
