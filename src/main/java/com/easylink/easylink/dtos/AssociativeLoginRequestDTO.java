package com.easylink.easylink.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AssociativeLoginRequestDTO {
    private String id;
    private String email;
    private String timezone;
    private List<AssociativeAnswerDTO> answers;
}
