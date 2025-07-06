package com.easylink.easylink.vibe_service.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("PHONE")
public class PhoneField extends VibeField{

    private String phone;

    @Override
    public String getType() {
        return "phone";
    }

    @Override
    public String getValue() {
        return this.phone;
    }
}
