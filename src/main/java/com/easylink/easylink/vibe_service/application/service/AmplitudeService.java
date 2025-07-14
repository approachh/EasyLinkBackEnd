package com.easylink.easylink.vibe_service.application.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.Map;
import java.util.Objects;

@Service
public class AmplitudeService {

    @Value("${amplitude.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String AMPLITUDE_URL = "https://api2.amplitude.com/2/httpapi";

    public void sendEvent(String userId, String eventType, Map<String, Object> eventProps){

        String payload = buildPayLoad(userId, eventType, eventProps);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(payload,headers);

        restTemplate.postForObject(AMPLITUDE_URL,request, String.class);
    }

    private String buildPayLoad(String userId,String eventType, Map<String, Object> eventProps) {
        StringBuilder propsBuilder = new StringBuilder();
        if (eventProps != null && !eventProps.isEmpty()) {
            propsBuilder.append(",");
            propsBuilder.append("\"event_properties\":{");

            int i = 0;
            for (Map.Entry<String, Object> entry : eventProps.entrySet()) {
                propsBuilder.append("\"").
                        append(entry.getKey())
                        .append("\":");
                if (entry.getValue() instanceof Number) {
                    propsBuilder.append(entry.getValue());
                } else {
                    propsBuilder.append("\"").append(entry.getValue()).append("\"");
                }
                if (i < eventProps.size() - 1) {
                    propsBuilder.append(",");
                }
                i++;
            }
            propsBuilder.append("}");
        }
        return """
                        {
                  "api_key": "%s",
                  "events": [
                    {
                      "user_id": "%s",
                      "event_type": "%s"%s
                    }
                  ]
                }
                """.formatted(apiKey, userId, eventType, propsBuilder);
    }
}
