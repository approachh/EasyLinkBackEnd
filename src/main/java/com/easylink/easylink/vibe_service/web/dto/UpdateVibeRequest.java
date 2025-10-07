package com.easylink.easylink.vibe_service.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class UpdateVibeRequest {
    private String name;
    private String description;
    private List<UUID> fieldIds;
    private String photo;
    private List<VibeFieldDTO> fieldsDTO; // вот тут массив объектов, а не id!
}
