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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "real_question")
    private QuestionTemplate realQuestion;

    @Column(nullable = false)
    private String associativeQuestion;

    @Column(nullable = false)
    private String answerHash;

    @ManyToOne
    @JoinColumn(name = "vibe_account")
    private VibeAccount vibeAccount;

}
