package com.easylink.easylink.vibe_service.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateItemRequest {
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
}
