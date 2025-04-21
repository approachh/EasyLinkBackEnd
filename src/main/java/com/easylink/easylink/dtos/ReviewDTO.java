package com.easylink.easylink.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class ReviewDTO {

    private String username;
    private String content;
    private LocalDateTime createdAt;

}
