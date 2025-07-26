package com.easylink.easylink.vibe_service.infrastructure.repository;

import com.easylink.easylink.vibe_service.application.port.out.VibeRepositoryPort;
import com.easylink.easylink.vibe_service.domain.model.Vibe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class JpaVibeRepositoryAdapter  implements VibeRepositoryPort {

    private final SpringDataVibeRepository delegate;

    @Override
    public Vibe save(Vibe vibe){
        return  delegate.save(vibe);
    }

    @Override
    public Optional<Vibe> findById(UUID id) {
        return delegate.findById(id);
    }

    @Override
    public void delete(Vibe vibe) {
        delegate.delete(vibe);
    }

    @Override
    public List<Vibe> findAllByAccountId(UUID id) {
        return delegate.findAllByVibeAccountId(id);
    }

    @Override
    public List<Vibe> findAllByUsername(String username) {
        return List.of();
    }

    @Override
    public List<Vibe> findAllById(UUID id) {
        return delegate.findAllById(id);
    }

    @Override
    public Optional<Vibe> findByPublicCodeAndVisibleTrue(String publicCode) {
        return delegate.findByPublicCodeAndVisibleTrue(publicCode);
    }
}
