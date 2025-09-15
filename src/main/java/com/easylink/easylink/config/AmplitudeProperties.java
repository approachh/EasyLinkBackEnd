package com.easylink.easylink.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "amplitude")
public class AmplitudeProperties {
    private String apiKey;
    private  String secretKey;
    private String exportUrl;
}
