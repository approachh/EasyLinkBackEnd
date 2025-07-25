package com.easylink.easylink.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpDTO {
    @NotBlank(message = "Email can't be empty")
    private String email;

    private List<AssociativeEntryDTO> entries;
}
