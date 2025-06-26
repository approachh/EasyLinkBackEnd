package com.easylink.easylink.vibe_service.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class UpdateVibeCommand {
    private UUID id;
    private UUID accountId;
    private String title;
    private List<UUID> fieldIds;
}
