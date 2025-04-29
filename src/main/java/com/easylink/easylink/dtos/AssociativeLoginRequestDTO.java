package com.easylink.easylink.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssociativeLoginRequestDTO {
    private String email;
    private String timezone;
    private List<AssociativeAnswerDTO> answers;
}
