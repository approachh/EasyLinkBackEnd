package com.easylink.easylink.etl.infrastructure;

import com.easylink.easylink.etl.application.ports.out.AmplitudeEventRepositoryPort;
import com.easylink.easylink.etl.domain.AmplitudeRawEvent;
import com.easylink.easylink.vibe_service.domain.model.AmplitudeEvent;
import com.easylink.easylink.vibe_service.infrastructure.repository.SpringAmplitudeEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AmplitudeEventSaver implements AmplitudeEventRepositoryPort {

    private final SpringAmplitudeEventRepository repository;

    @Override
    public void saveAll(List<AmplitudeRawEvent> events) {
        for(AmplitudeRawEvent raw:events.toArray(new AmplitudeRawEvent[0])){
            AmplitudeEvent event = AmplitudeEvent.builder()
                    .userId(raw.userId())
                    .eventType(raw.eventType())
                    .insertId(raw.insertId())
                    .serverUploadTime(Instant.ofEpochMilli(raw.serverUploadTime()))
                    .userProperties(raw.userProperties())
                    .build();
            repository.save(event);
        }
    }
}
