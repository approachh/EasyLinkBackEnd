package com.easylink.easylink.vibe_service.application.port.out;

import com.easylink.easylink.vibe_service.application.dto.ItemDTO;
import com.easylink.easylink.vibe_service.domain.model.Item;
import com.easylink.easylink.vibe_service.web.dto.UpdateItemRequest;

import java.util.UUID;

public interface CatalogUpdateRepositoryPort {
    ItemDTO updateItem(UUID id, UpdateItemRequest updateItemRequest);
}
