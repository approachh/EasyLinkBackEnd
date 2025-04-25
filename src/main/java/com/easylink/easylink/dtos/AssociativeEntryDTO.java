package com.easylink.easylink.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssociativeEntryDTO {

    private QuestionTemplateDTO realQuestion;
    @NotBlank
    private String associativeQuestion;
    @NotBlank()
    private String answer;
}
