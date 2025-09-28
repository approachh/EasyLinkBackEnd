package com.easylink.easylink.review_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(length = 1000)
    private String content;

    private LocalDateTime createdAt;

    private Integer rating = 5;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
