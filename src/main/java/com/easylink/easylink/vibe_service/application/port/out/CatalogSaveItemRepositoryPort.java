package com.easylink.easylink.vibe_service.application.port.out;

import com.easylink.easylink.vibe_service.domain.model.Item;
import com.easylink.easylink.vibe_service.web.dto.CreateItemRequest;

public interface CatalogSaveItemRepositoryPort {
    Item save(Item item);
}
