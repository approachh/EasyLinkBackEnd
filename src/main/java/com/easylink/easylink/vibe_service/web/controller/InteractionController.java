package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.application.port.in.interaction.CreateInteractionUseCase;
import com.easylink.easylink.vibe_service.application.service.InteractionService;
import com.easylink.easylink.vibe_service.domain.interaction.Interaction;
import com.easylink.easylink.vibe_service.infrastructure.repository.SpringDataVibeRepository;
import com.easylink.easylink.vibe_service.web.dto.CreateInteractionRequest;
import com.easylink.easylink.vibe_service.web.dto.InteractionResponse;
import com.easylink.easylink.vibe_service.web.dto.VibeResponse;
import com.easylink.easylink.vibe_service.web.mapper.VibeResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v3/interactions")
@RequiredArgsConstructor
public class InteractionController {

    private final CreateInteractionUseCase createInteractionUseCase;
    private final InteractionService interactionService;

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

    @RequestMapping("/followers")
    public ResponseEntity<VibeResponse> getFollowers(@RequestParam UUID vibeId,@AuthenticationPrincipal Jwt jwt){

        return ResponseEntity.ok(null);
    }

}
