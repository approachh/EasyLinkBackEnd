package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.vibe_service.web.dto.VibeFieldDTO;
import com.easylink.easylink.vibe_service.application.port.in.field.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v3/vibe-fields")
@RequiredArgsConstructor
public class VibeFieldController {

    private final CreateVibeFieldUseCase createVibeFieldUseCase;
    private final UpdateVibeFieldUseCase updateVibeFieldUseCase;
    private final DeleteVibeFieldUseCase deleteVibeFieldUseCase;
    private final GetVibeFieldUseCase getVibeFieldUseCase;

    // Создать поле
    @PostMapping
    public ResponseEntity<VibeFieldDTO> create(@RequestBody VibeFieldDTO request) {
        VibeFieldDTO created = createVibeFieldUseCase.create(request);
        return ResponseEntity.ok(created);
    }

    // Получить поле по id
    @GetMapping("/{id}")
    public ResponseEntity<VibeFieldDTO> get(@PathVariable UUID id) {
        VibeFieldDTO field = getVibeFieldUseCase.getById(id);
        return ResponseEntity.ok(field);
    }

    // Обновить поле
    @PutMapping("/{id}")
    public ResponseEntity<VibeFieldDTO> update(@PathVariable UUID id, @RequestBody VibeFieldDTO request) {
        request.setId(id);
        VibeFieldDTO updated = updateVibeFieldUseCase.update(request);
        return ResponseEntity.ok(updated);
    }

    // Удалить поле
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteVibeFieldUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Получить все поля для одного вайба
    @GetMapping
    public ResponseEntity<List<VibeFieldDTO>> getAll(@RequestParam UUID vibeId) {
        List<VibeFieldDTO> fields = getVibeFieldUseCase.getAllByVibeId(vibeId);
        return ResponseEntity.ok(fields);
    }
}
