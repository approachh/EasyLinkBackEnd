package com.easylink.easylink.vibe_service.application.port.out;

import com.easylink.easylink.vibe_service.application.dto.ItemDTO;
import com.easylink.easylink.vibe_service.domain.model.Item;

import java.util.List;
import java.util.UUID;

public interface CatalogRepositoryPort {
    List<ItemDTO> getAllItemsByVibeId(UUID vibeId);
}
