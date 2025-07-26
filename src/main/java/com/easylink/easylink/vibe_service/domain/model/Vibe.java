package com.easylink.easylink.vibe_service.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Vibe {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID vibeAccountId;

    private String description;

    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VibeType type;

    private Boolean visible;

    private String publicCode;

    private LocalDateTime codeGeneratedAt;

    @OneToMany(mappedBy = "vibe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VibeField> fields = new ArrayList<>();
}
