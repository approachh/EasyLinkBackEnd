package com.easylink.easylink.vibe_service.application.port.in.catalog;

import com.easylink.easylink.vibe_service.domain.model.Item;
import com.easylink.easylink.vibe_service.web.dto.CreateItemRequest;

public interface CreateItemUseCase {
    Item saveItem(CreateItemRequest createItemRequest);
}
