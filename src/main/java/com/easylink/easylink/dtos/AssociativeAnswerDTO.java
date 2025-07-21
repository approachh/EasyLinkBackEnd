package com.easylink.easylink.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AssociativeAnswerDTO {
    private UUID entryId;
    private String answer;
}
