package com.easylink.easylink.etl_service.infrastructure;

import com.easylink.easylink.etl_service.application.ports.out.AmplitudeEventRepositoryPort;
import com.easylink.easylink.etl_service.domain.AmplitudeRawEvent;
import com.easylink.easylink.vibe_service.domain.model.AmplitudeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AmplitudeEventSaver implements AmplitudeEventRepositoryPort {

    private final SpringAmplitudeEventRepository repository;

    @Override
    public void saveAll(List<AmplitudeEvent> events) {
        repository.saveAll(events);
    }
}
