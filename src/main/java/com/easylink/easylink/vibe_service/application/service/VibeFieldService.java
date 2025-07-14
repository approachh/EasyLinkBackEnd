package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.domain.model.VibeField;
import com.easylink.easylink.vibe_service.domain.model.PublicTextVibeField;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import com.easylink.easylink.vibe_service.infrastructure.repository.VibeFieldRepository;
import com.easylink.easylink.vibe_service.application.port.out.VibeRepositoryPort;
import com.easylink.easylink.vibe_service.web.dto.VibeFieldDTO;
import com.easylink.easylink.vibe_service.application.port.in.field.CreateVibeFieldUseCase;
import com.easylink.easylink.vibe_service.application.port.in.field.UpdateVibeFieldUseCase;
import com.easylink.easylink.vibe_service.application.port.in.field.DeleteVibeFieldUseCase;
import com.easylink.easylink.vibe_service.application.port.in.field.GetVibeFieldUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VibeFieldService implements
        CreateVibeFieldUseCase,
        UpdateVibeFieldUseCase,
        DeleteVibeFieldUseCase,
        GetVibeFieldUseCase {

    private final VibeFieldRepository vibeFieldRepository;
    private final VibeRepositoryPort vibeRepository; // для установки связи с Vibe

    @Override
    public VibeFieldDTO create(VibeFieldDTO request) {
        // --- создаём конкретного наследника ---
        PublicTextVibeField entity = new PublicTextVibeField();
        entity.setLabel(request.getLabel());
        entity.setType(request.getType());
        entity.setValue(request.getValue());

        // Найти Vibe по id и установить связь
        if (request.getVibeId() != null) {
            Vibe vibe = vibeRepository.findById(request.getVibeId())
                    .orElseThrow(() -> new RuntimeException("Vibe not found"));
            entity.setVibe(vibe);
        }

        VibeField saved = vibeFieldRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public VibeFieldDTO update(VibeFieldDTO request) {
        VibeField field = vibeFieldRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Field not found"));
        field.setType(request.getType());
        field.setLabel(request.getLabel());
        field.setValue(request.getValue());
        // обновить связь с вайбом, если надо
        if (request.getVibeId() != null) {
            Vibe vibe = vibeRepository.findById(request.getVibeId())
                    .orElseThrow(() -> new RuntimeException("Vibe not found"));
            field.setVibe(vibe);
        }
        VibeField saved = vibeFieldRepository.save(field);
        return toDto(saved);
    }

    @Override
    public void delete(UUID id) {
        vibeFieldRepository.deleteById(id);
    }

    @Override
    public VibeFieldDTO getById(UUID id) {
        VibeField field = vibeFieldRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field not found"));
        return toDto(field);
    }

    @Override
    public List<VibeFieldDTO> getAllByVibeId(UUID vibeId) {
        List<VibeField> fields = vibeFieldRepository.findAllByVibe_Id(vibeId);
        return fields.stream().map(this::toDto).collect(Collectors.toList());
    }

    // --- Вспомогательный метод для преобразования entity <-> DTO ---
    private VibeFieldDTO toDto(VibeField entity) {
        return new VibeFieldDTO(
                entity.getId(),
                entity.getType(),
                entity.getValue(),
                entity.getLabel(),
                entity.getVibe() != null ? entity.getVibe().getId() : null
        );
    }
}
