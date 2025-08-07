package com.easylink.easylink.vibe_service.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ItemDTO {
    private UUID id;
    private UUID vibeId;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
}
