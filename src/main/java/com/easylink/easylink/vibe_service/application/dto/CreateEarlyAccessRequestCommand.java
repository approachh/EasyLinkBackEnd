package com.easylink.easylink.vibe_service.application.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;

public class CreateEarlyAccessRequestCommand {

    private String email;

    private String source; // just in case

    private Instant createdAt = Instant.now();

    private boolean approved;
}
