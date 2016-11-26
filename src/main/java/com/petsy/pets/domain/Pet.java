package com.petsy.pets.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    public boolean isValid() {
        return StringUtils.isNotEmpty(this.name);
    }
}
