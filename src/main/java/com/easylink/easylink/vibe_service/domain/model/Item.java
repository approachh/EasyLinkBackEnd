package com.easylink.easylink.vibe_service.domain.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
}
