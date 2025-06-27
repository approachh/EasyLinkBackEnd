package com.easylink.easylink.vibe_service.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VibeFieldDTO {
    private UUID id;
    private String type;
    private String value;
    private String label;
}
