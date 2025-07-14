package com.easylink.easylink.vibe_service.infrastructure.repository;


import com.easylink.easylink.vibe_service.application.port.out.VibeFieldRepositoryPort;
import com.easylink.easylink.vibe_service.domain.model.VibeField;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public class JpaVibeFieldRepositoryAdapter implements VibeFieldRepositoryPort {

    private final SpringDataVibeFieldRepository springDataVibeFieldRepository;

    public JpaVibeFieldRepositoryAdapter(SpringDataVibeFieldRepository springDataVibeFieldRepository){
        this.springDataVibeFieldRepository = springDataVibeFieldRepository;
    }
    @Override
    public Optional<VibeField> findById(UUID id) {
        return springDataVibeFieldRepository.findById(id);
    }

    @Override
    public List<VibeField> findAllById(List<UUID> ids) {

        List<VibeField> vibeFields = springDataVibeFieldRepository.findAllById(ids);

        return vibeFields;
    }

    public VibeField save(VibeField vibeField){

        return springDataVibeFieldRepository.save(vibeField);
    }

    @Override
    public List<VibeField> findAllByVibeId(UUID id) {
        return springDataVibeFieldRepository.findAllByVibeId(id);
    }
}
