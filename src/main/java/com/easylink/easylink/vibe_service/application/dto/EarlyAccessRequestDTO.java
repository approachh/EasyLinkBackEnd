package com.easylink.easylink.vibe_service.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EarlyAccessRequestDTO {
    private String email;
    private String source; // just in case
    private Instant createdAt;
    private boolean approved;
}
