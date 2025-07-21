package com.easylink.easylink.vibe_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum VibeType {
    BUSINESS("Business vibe"),
    PERSONAL("Personal vibe"),
    OTHER("Events,Temporary, Any other kind");

    private final String description;

    VibeType(String description){
        this.description = description;
    }
}
