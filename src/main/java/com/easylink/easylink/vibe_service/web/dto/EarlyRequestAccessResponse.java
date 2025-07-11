package com.easylink.easylink.vibe_service.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class EarlyRequestAccessResponse {
    private String email;
    private String source;
    private Instant createdAt;
    private boolean approved;
}
