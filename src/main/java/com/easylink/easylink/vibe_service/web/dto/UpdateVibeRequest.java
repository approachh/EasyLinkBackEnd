package com.easylink.easylink.vibe_service.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class UpdateVibeRequest {
    private String title;
    private List<UUID> fieldIds;
}
