package com.easylink.easylink.review_service.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDTO {
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private Integer rating;
}
