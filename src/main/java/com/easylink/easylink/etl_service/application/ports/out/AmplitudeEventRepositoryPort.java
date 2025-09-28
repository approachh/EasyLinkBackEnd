package com.easylink.easylink.etl_service.application.ports.out;

import com.easylink.easylink.etl_service.domain.AmplitudeRawEvent;

import java.util.List;

public interface AmplitudeEventRepositoryPort {
    void saveAll(List<AmplitudeRawEvent> events);
}
