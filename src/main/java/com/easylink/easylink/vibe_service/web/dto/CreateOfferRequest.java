package com.easylink.easylink.vibe_service.web.dto;

import com.easylink.easylink.vibe_service.domain.interaction.offer.DiscountType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateOfferRequest {
    private UUID vibeId;
    private String title;
    private String description;
    private DiscountType discountType;
    private int initialDiscount;
    private int currentDiscount;
    private int decreaseStep;
    private int decreaseIntervalMinutes;
    private boolean active;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
