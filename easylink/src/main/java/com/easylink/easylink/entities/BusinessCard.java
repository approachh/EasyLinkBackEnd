package com.easylink.easylink.entities;

import com.easylink.easylink.enums.TypeBusinessCard;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BusinessCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Person person;

    private TypeBusinessCard typeBusinessCard;

//    @OneToMany(mappedBy="person",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<AbstractContactDetail> contacts = new ArrayList<>();


}
