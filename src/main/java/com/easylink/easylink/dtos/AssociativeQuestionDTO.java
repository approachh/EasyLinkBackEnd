package com.easylink.easylink.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssociativeQuestionDTO {
    private UUID entryId;
    private String question;

    public AssociativeQuestionDTO(UUID id, String associativeQuestion) {
        this.entryId = id;
        this.question = associativeQuestion;
    }
}
