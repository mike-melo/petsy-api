package com.petsy.pets;

import com.petsy.pets.domain.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pets", path = "pets")
public interface PetRepository extends CrudRepository<Pet, Long> {
}
