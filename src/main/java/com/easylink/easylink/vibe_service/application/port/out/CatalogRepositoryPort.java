package com.easylink.easylink.vibe_service.application.port.out;

import com.easylink.easylink.vibe_service.domain.model.Item;

import java.util.List;

public interface CatalogRepositoryPort {
    List<Item> getAllItemsByVibeId(Long vibeId);
}
