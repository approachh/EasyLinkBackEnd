package com.easylink.easylink.vibe_service.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("LINK")
public class LinkField extends VibeField{

    private String value;


    @Override
    public String getType() {
        return "link";
    }

    @Override
    public String getValue() {
        return value;
    }
}
