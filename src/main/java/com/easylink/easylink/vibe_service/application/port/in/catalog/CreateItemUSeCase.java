package com.easylink.easylink.vibe_service.application.port.in.catalog;

import com.easylink.easylink.vibe_service.domain.model.Item;

import java.util.List;

public interface CreateItemUSeCase {
    Item saveItem(Long vibeId, Item item);
}
