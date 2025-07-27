package com.easylink.easylink.vibe_service.web.dto;

import com.easylink.easylink.vibe_service.domain.model.VibeType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateVibeRequest {
    private String description;
    private VibeType type;
    private String name;
    private Boolean visible;
    private String publicCode;
    private List<VibeFieldDTO> fieldsDTO;
}
