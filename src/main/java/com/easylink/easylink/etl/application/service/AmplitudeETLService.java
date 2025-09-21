package com.easylink.easylink.etl.application.service;

import com.easylink.easylink.config.AmplitudeProperties;
import com.easylink.easylink.etl.application.ports.in.FetchAndSaveEventsUseCase;
import com.easylink.easylink.etl.application.ports.out.AmplitudeEventRepositoryPort;
import com.easylink.easylink.etl.application.ports.out.AmplitudeExportPort;
import com.easylink.easylink.etl.application.ports.out.AmplitudeParserPort;
import com.easylink.easylink.etl.domain.AmplitudeRawEvent;
import com.easylink.easylink.vibe_service.domain.model.AmplitudeEvent;
import com.easylink.easylink.vibe_service.infrastructure.repository.SpringAmplitudeEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

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

//
//    private final SpringAmplitudeEventRepository amplitudeEventRepository;
//    private final AmplitudeProperties amplitudeProperties;
//
//    public void fetchAndSave(String start, String end) {
//        String apiKey = amplitudeProperties.getApiKey();
//        String secretKey = amplitudeProperties.getSecretKey();
//
//        String url = String.format("%s?start=%s&end=%s",
//                amplitudeProperties.getExportUrl(),
//                start, end);
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(apiKey, secretKey);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<byte[]> response = restTemplate.exchange(
//                url, HttpMethod.GET, entity, byte[].class
//        );
//
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            throw new RuntimeException("Request failed: " + response.getStatusCode());
//        }
//
//        byte[] body = response.getBody();
//        if (body == null || body.length == 0) {
//            throw new RuntimeException("Empty response body");
//        }
//
//        try {
//            File tempZip = File.createTempFile("amplitude", ".zip");
//            try (FileOutputStream fos = new FileOutputStream(tempZip)) {
//                fos.write(body);
//            }
//
//            ObjectMapper mapper = new ObjectMapper();
//            int totalEvents = 0;
//
//            try (ZipFile zipFile = new ZipFile(tempZip)) {
//                Enumeration<? extends ZipEntry> entries = zipFile.entries();
//
//                while (entries.hasMoreElements()) {
//                    ZipEntry entry = entries.nextElement();
//                    System.out.println("Found file in zip: " + entry.getName());
//
//
//                    try (InputStream is = zipFile.getInputStream(entry);
//                         GZIPInputStream gis = new GZIPInputStream(is);
//                         BufferedReader br = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8))) {
//
//                        String line;
//                        while ((line = br.readLine()) != null) {
//                            JsonNode node = mapper.readTree(line);
//
//                            AmplitudeEvent event = AmplitudeEvent.builder()
//                                    .userId(node.path("user_id").asText(null))
//                                    .eventType(node.path("event_type").asText(null))
//                                    .insertId(node.path("insert_id").asText(null))
//                                    .serverUploadTime(
//                                            Instant.ofEpochMilli(node.path("server_upload_time").asLong(0))
//                                    )
//                                    .userProperties(node.path("event_properties").toString())
//                                    .build();
//
//                            amplitudeEventRepository.save(event);
//                            totalEvents++;
//                        }
//                    }
//                }
//            }
//
//            System.out.println("Saved " + totalEvents + " events");
//
//        } catch (IOException e) {
//            throw new RuntimeException("Error processing Amplitude export", e);
//        }
//    }

}
