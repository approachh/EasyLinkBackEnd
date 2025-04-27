package com.easylink.easylink.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
//@NoArgsConstructor
@RequiredArgsConstructor
public class VibeAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;
    @OneToMany(mappedBy = "vibeAccount", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AssociativeEntry> associativeEntries;

    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private int failedAttempts;
    private LocalDateTime lockTime;

}
