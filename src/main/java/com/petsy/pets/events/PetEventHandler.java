package com.petsy.pets.events;

import com.petsy.pets.domain.Pet;
import com.petsy.exceptions.ValidationException;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler(Pet.class)
public class PetEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(Pet pet) {
        if(!pet.isValid()) {
            throw new ValidationException();
        }
    }
}
