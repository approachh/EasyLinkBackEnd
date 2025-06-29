package com.easylink.easylink.vibe_service.web.mapper;

import com.easylink.easylink.vibe_service.application.dto.CreateVibeCommand;
import com.easylink.easylink.vibe_service.web.dto.CreateVibeRequest;

import java.util.UUID;

public class VibeRequestMapper {
    public static CreateVibeCommand toCommand(CreateVibeRequest request){
        return new CreateVibeCommand(request.getDescription(), request.getType(),request.getFieldsDTO());
    }
}
