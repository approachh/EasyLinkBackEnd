package com.easylink.easylink.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionTemplateDTO {
    private String text;
    private boolean predefined;
    private LocalDateTime createdAt;
}
