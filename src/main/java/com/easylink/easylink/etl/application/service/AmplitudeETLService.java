package com.easylink.easylink.etl.application.service;

import com.easylink.easylink.config.AmplitudeProperties;
import com.easylink.easylink.etl.application.ports.in.FetchAndSaveEventsUseCase;
import com.easylink.easylink.etl.application.ports.out.AmplitudeEventRepositoryPort;
import com.easylink.easylink.etl.application.ports.out.AmplitudeExportPort;
import com.easylink.easylink.etl.application.ports.out.AmplitudeParserPort;
import com.easylink.easylink.etl.domain.AmplitudeRawEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class AmplitudeETLService implements FetchAndSaveEventsUseCase {

    private final AmplitudeExportPort amplitudeExportPort;
    private final AmplitudeParserPort amplitudeParserPort;
    private final AmplitudeEventRepositoryPort amplitudeEventRepositoryPort;

    @Override
    public void fetchAndSave(String start,String end){
        File zip = amplitudeExportPort.fetch(start,end);
        List<AmplitudeRawEvent> events = amplitudeParserPort.parse(zip);
        amplitudeEventRepositoryPort.saveAll(events);
    };
}
