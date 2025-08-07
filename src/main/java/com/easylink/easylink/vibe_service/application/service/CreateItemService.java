package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.dto.CreateItemCommand;
import com.easylink.easylink.vibe_service.application.dto.ItemDTO;
import com.easylink.easylink.vibe_service.application.port.in.catalog.CreateItemUseCase;
import com.easylink.easylink.vibe_service.application.port.out.CatalogSaveItemRepositoryPort;
import com.easylink.easylink.vibe_service.application.port.out.VibeRepositoryPort;
import com.easylink.easylink.vibe_service.domain.model.Item;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateItemService implements CreateItemUseCase {

    private final CatalogSaveItemRepositoryPort catalogSaveItemRepositoryPort;
    private final ModelMapper modelMapper;
    private final VibeRepositoryPort vibeRepositoryPort;


    @Override
    public ItemDTO saveItem(CreateItemCommand createItemCommand) {

        UUID vibeId = createItemCommand.getVibeId();
        Vibe vibe = vibeRepositoryPort.findById(vibeId)
                .orElseThrow(() -> new IllegalArgumentException("Vibe not found: " + vibeId));

        Item item = new Item(vibe,
                createItemCommand.getTitle(),
                createItemCommand.getDescription(),
                createItemCommand.getImageUrl(),
                createItemCommand.getPrice());


        Item savedItem = catalogSaveItemRepositoryPort.save(item);

        ItemDTO itemDTO = modelMapper.map(savedItem, ItemDTO.class);

        return itemDTO;
    }
}
