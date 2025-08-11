package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.vibe_service.application.port.in.vibe_visibility.SetVisibilityUseCase;
import com.easylink.easylink.vibe_service.application.service.VibeVisibilityService;
import com.easylink.easylink.vibe_service.web.dto.VibeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/vibes/visibility")
public class VibeVisibilityController {

    private final SetVisibilityUseCase setVisibilityUse;
    private final VibeVisibilityService vibeVisibilityService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> setVisibilityVibe(@PathVariable UUID id, @RequestParam boolean visible, @AuthenticationPrincipal Jwt jwt){

        if(visible){
            String code = setVisibilityUse.setVisibility(id);
            return ResponseEntity.ok(Map.of("code",code));
        }else{
            setVisibilityUse.cancelVisibility(id);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<VibeResponse> getVibeByCode(@PathVariable String code){

        return  ResponseEntity.ok(vibeVisibilityService.findByPublicCodeAndVisibleTrue(code));

    }

//    @GetMapping("/id/{vibeId}/code")
//    public ResponseEntity findCodeByVibeId(@PathVariable UUID vibeId,@AuthenticationPrincipal Jwt jwt){
//
//        return  ResponseEntity.ok(vibeVisibilityService.findCodeByVibeId(vibeId));
//
//    }
}
