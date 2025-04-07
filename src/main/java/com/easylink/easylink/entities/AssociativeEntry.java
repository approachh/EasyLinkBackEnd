package com.easylink.easylink.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class AssociativeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String realQuestion;
    @Column(nullable = false)
    private String associativeQuestion;
    @Column(nullable = false)
    private String answerHash;
}
