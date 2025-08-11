package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.exceptions.NotFoundException;
import com.easylink.easylink.vibe_service.application.dto.ItemDTO;
import com.easylink.easylink.vibe_service.application.port.out.CatalogUpdateRepositoryPort;
import com.easylink.easylink.vibe_service.domain.model.Item;
import com.easylink.easylink.vibe_service.infrastructure.repository.JpaCatalogRepositoryAdapter;
import com.easylink.easylink.vibe_service.web.dto.UpdateItemRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.easylink.easylink.vibe_service.application.port.out.CatalogRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CatalogService implements CatalogRepositoryPort, CatalogUpdateRepositoryPort {

    private final JpaCatalogRepositoryAdapter jpaCatalogRepositoryAdapter;
    private final ModelMapper modelMapper;

    @Override
    public List<ItemDTO> getAllItemsByVibeId(UUID vibeId) {

        List<Item> itemList = jpaCatalogRepositoryAdapter.getAllItemsByVibeId(vibeId);

        List<ItemDTO> itemDTOList = itemList.stream().map(item->modelMapper.map(item, ItemDTO.class)).toList();

        return itemDTOList;
    }

    public ItemDTO getById(UUID vibeId) {

        Optional<Item> optionalItem = jpaCatalogRepositoryAdapter.getById(vibeId);

        ItemDTO itemDTO = modelMapper.map(optionalItem.orElseThrow(()->new RuntimeException("No item found by ID")), ItemDTO.class);

        return itemDTO;
    }


    @Override
    public ItemDTO updateItem(UUID id, UpdateItemRequest updateItemRequest) {

        Item item = jpaCatalogRepositoryAdapter.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        if (updateItemRequest.getTitle() != null) {
            item.setTitle(updateItemRequest.getTitle());
        }
        if (updateItemRequest.getDescription() != null) {
            item.setDescription(updateItemRequest.getDescription());
        }
        if (updateItemRequest.getPrice() != null) {
            item.setPrice(updateItemRequest.getPrice());
        }
        if (updateItemRequest.getImageUrl() != null) {
            item.setImageUrl(updateItemRequest.getImageUrl());
        }

        return modelMapper.map(jpaCatalogRepositoryAdapter.save(item), ItemDTO.class);

    }
}
