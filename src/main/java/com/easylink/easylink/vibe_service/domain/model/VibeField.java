package com.easylink.easylink.vibe_service.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@DiscriminatorColumn(name = "field_type")
public abstract class VibeField {

    @Id
    @GeneratedValue
    private UUID id;

    private String label;
    private String type;
    protected String value;

    @ManyToOne
    @JoinColumn(name = "vibe_id")
    private Vibe vibe;

    private Long accountVibeId;


    public abstract String getType();
    public abstract String getValue();
}
