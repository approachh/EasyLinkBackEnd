package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.port.in.catalog.CreateItemUseCase;
import com.easylink.easylink.vibe_service.domain.model.Item;
import com.easylink.easylink.vibe_service.infrastructure.repository.SpringDataCatalogRepository;
import com.easylink.easylink.vibe_service.web.dto.CreateItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateItemService implements CreateItemUseCase {

    private final SpringDataCatalogRepository springDataCatalogRepository;

    @Override
    public Item saveItem(CreateItemRequest createItemRequest) {



        return null;
    }
}
