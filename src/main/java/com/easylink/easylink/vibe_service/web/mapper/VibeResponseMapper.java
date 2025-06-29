package com.easylink.easylink.vibe_service.web.mapper;

import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.web.dto.VibeResponse;

public class VibeResponseMapper {
    public static VibeResponse toResponse(VibeDto vibeDto){
        VibeResponse response = new VibeResponse();
        response.setId(vibeDto.getId());
        response.setDescription(vibeDto.getDescription());
        response.setName(vibeDto.getName());
        response.setType(vibeDto.getType());
        response.setFieldsDTO(vibeDto.getFieldsDTO());
        return response;
    }
}
