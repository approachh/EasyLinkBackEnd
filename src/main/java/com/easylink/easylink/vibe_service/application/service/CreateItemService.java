package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.dto.CreateItemCommand;
import com.easylink.easylink.vibe_service.application.dto.ItemDTO;
import com.easylink.easylink.vibe_service.application.port.in.catalog.CreateItemUseCase;
import com.easylink.easylink.vibe_service.application.port.out.CatalogSaveItemRepositoryPort;
import com.easylink.easylink.vibe_service.domain.model.Item;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateItemService implements CreateItemUseCase {

    private final CatalogSaveItemRepositoryPort catalogSaveItemRepositoryPort;
    private final ModelMapper modelMapper;

    @Override
    public ItemDTO saveItem(CreateItemCommand createItemCommand) {

        Item item = new Item(createItemCommand.getTitle(),
                createItemCommand.getDescription(),
                createItemCommand.getImageUrl(),
                createItemCommand.getPrice());

        Item savedItem = catalogSaveItemRepositoryPort.save(item);

        ItemDTO itemDTO = modelMapper.map(savedItem, ItemDTO.class);

        return itemDTO;
    }
}
