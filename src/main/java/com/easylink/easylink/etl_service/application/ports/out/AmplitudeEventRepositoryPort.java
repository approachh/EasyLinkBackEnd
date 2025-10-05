package com.easylink.easylink.etl_service.application.ports.out;

import com.easylink.easylink.etl_service.domain.AmplitudeRawEvent;
import com.easylink.easylink.vibe_service.domain.model.AmplitudeEvent;

import java.util.List;

public interface AmplitudeEventRepositoryPort {
    void saveAll(List<AmplitudeEvent> events);
}
