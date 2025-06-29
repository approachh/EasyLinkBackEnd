package com.easylink.easylink.vibe_service.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class VibeResponse {
    private UUID id;
    private String description;
    private String type;
    private String name;
    private List<VibeFieldDTO> fieldsDTO;
}
