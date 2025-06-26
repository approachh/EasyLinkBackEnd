package com.easylink.easylink.vibe_service.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class VibeDto {
    private UUID id;
    private String title;
}
