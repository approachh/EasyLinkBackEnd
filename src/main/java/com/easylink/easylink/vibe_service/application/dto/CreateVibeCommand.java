package com.easylink.easylink.vibe_service.application.dto;

import com.easylink.easylink.vibe_service.domain.model.VibeField;
import com.easylink.easylink.vibe_service.web.dto.VibeFieldDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateVibeCommand {
    private String description;
    private List<VibeFieldDTO> vibeFieldsDTO;
    private UUID accountId;
}
