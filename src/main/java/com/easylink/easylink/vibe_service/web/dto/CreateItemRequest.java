package com.easylink.easylink.vibe_service.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateItemRequest {
    private UUID vibeId;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
}
