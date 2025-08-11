package com.easylink.easylink.vibe_service.application.port.in.catalog;

import com.easylink.easylink.vibe_service.application.dto.ItemDTO;
import com.easylink.easylink.vibe_service.application.dto.VibeDto;
import com.easylink.easylink.vibe_service.web.dto.UpdateVibeRequest;

import java.util.UUID;

public interface UpdateItemUseCase {
    ItemDTO update();
}
