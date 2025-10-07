package com.easylink.easylink.vibe_service.application.mapper;

import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import com.easylink.easylink.vibe_service.domain.model.VibeField;
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
        vibeDto.setPublicCode(vibe.getPublicCode());
        vibeDto.setVisible(vibe.getVisible());
        vibeDto.setPhoto(vibe.getPhoto());

        List<VibeFieldDTO> vibeFieldDTOS = vibe.getFields().stream().map(val ->
                new VibeFieldDTO(
                        val.getId(),
                        val.getType(),
                        val.getValue(),
                        val.getLabel(),
                        val.getVibe() != null ? val.getVibe().getId() : null
                )
        ).toList();
        vibeDto.setFieldsDTO(vibeFieldDTOS);
        return vibeDto;
    }
    private static VibeFieldDTO toDto(VibeField entity) {
        return new VibeFieldDTO(
                entity.getId(),
                entity.getType(),
                entity.getValue(),
                entity.getLabel(),
                entity.getVibe() != null ? entity.getVibe().getId() : null
        );
    }
}
