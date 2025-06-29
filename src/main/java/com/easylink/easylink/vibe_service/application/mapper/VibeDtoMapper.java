package com.easylink.easylink.vibe_service.application.mapper;

import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import com.easylink.easylink.vibe_service.web.dto.VibeFieldDTO;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class VibeDtoMapper {
    public static VibeDto toDto(Vibe vibe){
        VibeDto vibeDto = new VibeDto();
        vibeDto.setDescription(vibe.getDescription());
        vibeDto.setId(vibe.getId());
        vibeDto.setType(vibe.getType());
        vibeDto.setName(vibe.getName());

        List<VibeFieldDTO> vibeFieldDTOS = vibe.getFields().stream().map(val->
            new VibeFieldDTO(val.getId(),val.getType(),val.getValue(),val.getLabel())).toList();
        vibeDto.setFieldsDTO(vibeFieldDTOS);
        return vibeDto;
    }
}
