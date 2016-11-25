package com.petsy.pets.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Category category;

    private Status status;

}
