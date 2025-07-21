package com.easylink.easylink.vibe_service.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("inner_text")
public class PublicTextVibeField extends VibeField {
    @Override
    public String getType() { return "inner_text"; }
    @Override
    public String getValue() { return value; }
}
