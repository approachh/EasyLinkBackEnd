package com.easylink.easylink.etl_service.application.service;

import com.easylink.easylink.etl_service.application.ports.in.FetchAndSaveEventsUseCase;
import com.easylink.easylink.etl_service.application.ports.out.AmplitudeEventRepositoryPort;
import com.easylink.easylink.etl_service.application.ports.out.AmplitudeExportPort;
import com.easylink.easylink.etl_service.application.ports.out.AmplitudeParserPort;
import com.easylink.easylink.etl_service.domain.AmplitudeRawEvent;
import com.easylink.easylink.vibe_service.domain.model.AmplitudeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.io.*;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmplitudeETLService implements FetchAndSaveEventsUseCase {

    private final AmplitudeExportPort amplitudeExportPort;
    private final AmplitudeParserPort amplitudeParserPort;
    private final AmplitudeEventRepositoryPort amplitudeEventRepositoryPort;
    private final ModelMapper modelMapper;

    @Override
    public void fetchAndSave(String start, String end) {
        File zip = amplitudeExportPort.fetch(start, end);
        List<AmplitudeRawEvent> rawEvents = amplitudeParserPort.parse(zip);

        List<AmplitudeEvent> entities = rawEvents.stream()
                .map(raw -> AmplitudeEvent.builder()
                        .userId(raw.userId())
                        .eventType(raw.eventType())
                        .insertId(raw.insertId())
                        .serverUploadTime(Instant.ofEpochMilli(raw.serverUploadTime()))
                        .userProperties(raw.userProperties())
                        .eventProperties(raw.eventProperties())
                        .offerId(raw.offerId())
                        .build())
                .toList();


        amplitudeEventRepositoryPort.saveAll(entities);

        log.info("Saved {} Amplitude events", entities.size());
    }
}

