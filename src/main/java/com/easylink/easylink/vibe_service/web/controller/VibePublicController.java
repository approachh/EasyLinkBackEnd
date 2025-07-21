package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.application.port.in.vibe.*;
import com.easylink.easylink.vibe_service.web.dto.VibeResponse;
import com.easylink.easylink.vibe_service.web.mapper.VibeResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v3/vibes/public")
@RequiredArgsConstructor
public class VibePublicController {
    private final CreateVibeUseCase createVibeUseCase;
    private final UpdateVibeUseCase updateVibeUseCase;
    private final DeleteVibeUseCase deleteVibeUseCase;
    private final GetVibeUseCase getVibeUseCase;
    private final GetVibeByIdUseCase getVibeByIdUseCase;

    @Operation(summary = "Get profile", description = "Get Vibe profile using ID")
    @GetMapping("/{id}")
    public ResponseEntity<VibeResponse> getById(@PathVariable UUID id){

        VibeDto vibeDto = getVibeByIdUseCase.getVibeById(id);

        return ResponseEntity.ok(VibeResponseMapper.toResponse(vibeDto));
    }
}
