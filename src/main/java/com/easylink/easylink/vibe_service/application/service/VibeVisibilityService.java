package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.port.in.vibe_visibility.SetVisibilityUseCase;
import com.easylink.easylink.vibe_service.application.port.out.VibeRepositoryPort;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import com.easylink.easylink.vibe_service.web.dto.VibeResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VibeVisibilityService implements SetVisibilityUseCase {

    private final VibeRepositoryPort vibeRepositoryPort;
    private final CodeGenerationService codeGenerationService;
    private final ModelMapper modelMapper;

    @Override
    public String setVisibility(UUID vibeId) {

        Vibe foundVibe = vibeRepositoryPort.findById(vibeId).orElseThrow(()->new RuntimeException("Vibe not found"));

        String code = codeGenerationService.generateUniqueCode(codeCandidate->foundVibe!=null);

        foundVibe.setVisible(true);

        foundVibe.setPublicCode(code);

        foundVibe.setCodeGeneratedAt(LocalDateTime.now());

        vibeRepositoryPort.save(foundVibe);

        return code;
    }

    @Override
    public boolean cancelVisibility(UUID vibeId) {

        Vibe foundVibe = vibeRepositoryPort.findById(vibeId).orElseThrow(()->new RuntimeException("Vibe not found"));

        foundVibe.setVisible(false);

        foundVibe.setCodeGeneratedAt(LocalDateTime.now());

        vibeRepositoryPort.save(foundVibe);

        return true;
    }

    public VibeResponse findByPublicCodeAndVisibleTrue(String code){

        Vibe optionalVibe = vibeRepositoryPort.findByPublicCodeAndVisibleTrue(code).orElseThrow(()->new RuntimeException("Vibe nod found by code"));

        VibeResponse vibeResponse = modelMapper.map(optionalVibe, VibeResponse.class);

        return vibeResponse;
    }
}
