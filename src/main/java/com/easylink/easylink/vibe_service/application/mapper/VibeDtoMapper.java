package com.easylink.easylink.vibe_service.application.mapper;

import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.domain.model.Vibe;

public class VibeDtoMapper {
    public static VibeDto toDto(Vibe vibe){
        VibeDto vibeDto = new VibeDto();
        vibeDto.setTitle(vibe.getDescription());
        vibeDto.setId(vibe.getId());
        return vibeDto;
    }
}
