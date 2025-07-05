package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.vibe_service.application.port.in.interaction.CreateInteractionUseCase;
import com.easylink.easylink.vibe_service.infrastructure.repository.SpringDataVibeRepository;
import com.easylink.easylink.vibe_service.web.dto.CreateInteractionRequest;
import com.easylink.easylink.vibe_service.web.dto.InteractionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3/interactions")
@RequiredArgsConstructor
public class InteractionController {
    @Autowired
    CreateInteractionUseCase createInteractionUseCase;
    @PostMapping
    public ResponseEntity<InteractionResponse> create(@RequestBody CreateInteractionRequest createInteractionRequest, @AuthenticationPrincipal Jwt jwt){

        String vibeAccountId = jwt.getSubject();

        InteractionResponse interactionResponse = createInteractionUseCase.createInteraction(createInteractionRequest);



        return ;
    }
}
