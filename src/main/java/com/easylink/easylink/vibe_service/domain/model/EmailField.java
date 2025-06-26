package com.easylink.easylink.vibe_service.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("EMAIL")
public class EmailField extends VibeField{

    private String email;

    @Override
    public String getType() {
        return "email";
    }

    @Override
    public String getValue() {
        return email;
    }
}
