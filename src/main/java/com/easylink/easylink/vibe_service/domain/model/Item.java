package com.easylink.easylink.vibe_service.domain.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="vibe_id", nullable = false)
    private Vibe vibe;

    public Item(String title, String description, String imageUrl, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
    }

}
