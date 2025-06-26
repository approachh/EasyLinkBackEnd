package com.easylink.easylink.vibe_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateVibeCommand {
    private String title;
    private List<UUID> fieldIds;
    private UUID accountId;
}
