package com.easylink.easylink.vibe_service.domain.interaction.offer;

import com.easylink.easylink.vibe_service.domain.model.Vibe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Offer {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "vibe_id",nullable = false)
    private Vibe vibe;

    private String title;
    private String description;

    @Enumerated
    @Column(nullable = false)
    private DiscountType discountType;

    private int initialDiscount;
    private int currentDiscount;
    private int decreaseStep;
    private int decreaseIntervalMinutes;
    private boolean active;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
