package com.easylink.easylink.vibe_service.application.dto;

import com.easylink.easylink.vibe_service.web.dto.VibeFieldDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class UpdateVibeCommand {
    private UUID id;
    private UUID accountId;
    private String description;
    private List<VibeFieldDTO> fieldsDTO; // тут то же самое, список DTO
}
