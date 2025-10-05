package com.easylink.easylink.vibe_service.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "amplitude_events")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AmplitudeEvent {

    @Id
    @GeneratedValue
    private UUID id;

    private String userId;
    private String deviceId;
    private String eventType;
    private Instant eventTime;
    private String insertId;
    private Instant serverUploadTime;
    @Column(name = "offer_id")
    private String offerId;
    @Lob
    @Column(name = "event_properties")
    private String eventProperties;

    @Column(columnDefinition = "TEXT")
    private  String userProperties;

    private Instant insertTime;

    @PrePersist
    public void prePersist() {
        if (insertTime == null) {
            insertTime = Instant.now();
        }
    }
}
