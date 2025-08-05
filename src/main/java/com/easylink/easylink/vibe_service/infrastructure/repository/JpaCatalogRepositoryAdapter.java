package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.application.port.out.CatalogRepositoryPort;
import com.easylink.easylink.vibe_service.application.port.out.CatalogSaveItemRepositoryPort;
import com.easylink.easylink.vibe_service.domain.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaCatalogRepositoryAdapter implements CatalogSaveItemRepositoryPort {

    private final SpringDataCatalogRepository springDataCatalogRepository;

    @Override
    public Item save(Item item) {
        return springDataCatalogRepository.save(item);
    }
}
