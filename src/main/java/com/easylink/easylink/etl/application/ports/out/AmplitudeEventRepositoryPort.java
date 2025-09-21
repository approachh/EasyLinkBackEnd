package com.easylink.easylink.etl.application.ports.out;

import com.easylink.easylink.etl.domain.AmplitudeRawEvent;

import java.util.List;

public interface AmplitudeEventRepositoryPort {
    void saveAll(List<AmplitudeRawEvent> events);
}
