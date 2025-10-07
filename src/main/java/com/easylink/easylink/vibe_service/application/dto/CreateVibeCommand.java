package com.easylink.easylink.vibe_service.application.dto;

import com.easylink.easylink.vibe_service.domain.model.VibeField;
import com.easylink.easylink.vibe_service.domain.model.VibeType;
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
    private String name;
    private String description;
    private VibeType type;
    private String photo;
    private List<VibeFieldDTO> vibeFieldsDTO;
}
