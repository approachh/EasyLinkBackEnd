package com.easylink.easylink.vibe_service.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="early_access_request")
public class EarlyAccessRequest {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false,unique = true)
    private String email;

    private String source; // just in case

    private Instant createdAt = Instant.now();

    private boolean approved;
}
