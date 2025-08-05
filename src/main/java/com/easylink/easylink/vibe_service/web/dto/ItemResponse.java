package com.easylink.easylink.vibe_service.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemResponse {
    private UUID id;
    private UUID vibeId;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
}
