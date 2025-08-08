package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.dto.ItemDTO;
import com.easylink.easylink.vibe_service.domain.model.Item;
import com.easylink.easylink.vibe_service.infrastructure.repository.JpaCatalogRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.easylink.easylink.vibe_service.application.port.out.CatalogRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CatalogService implements CatalogRepositoryPort {

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

}
