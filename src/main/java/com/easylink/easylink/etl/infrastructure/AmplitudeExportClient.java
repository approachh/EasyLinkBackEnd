package com.easylink.easylink.etl.infrastructure;

import com.easylink.easylink.config.AmplitudeProperties;
import com.easylink.easylink.etl.application.ports.out.AmplitudeExportPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
@RequiredArgsConstructor
public class AmplitudeExportClient implements AmplitudeExportPort {

    private final AmplitudeProperties amplitudeProperties;
    private final WebClient webClient;

    @Override
    public File fetch(String start, String end) {

        String url = String.format("%s?start=%s&end=%s",
                amplitudeProperties.getExportUrl(), start, end);

        byte[] response = webClient.get()
                .uri(url)
                .headers(headers->headers.setBasicAuth(
                        amplitudeProperties.getApiKey(),
                        amplitudeProperties.getSecretKey()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
        if(response==null || response.length==0){
            throw new RuntimeException("Empty response from Amplitude");
        }

        try{
            File tempZip = File.createTempFile("amplitude",".zip");
            Files.write(tempZip.toPath(),response);
            return tempZip;
        }catch(IOException e){
            throw new RuntimeException("Failed to save zip to file",e);
        }
    }
}
