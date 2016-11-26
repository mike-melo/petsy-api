package com.petsy.pets;

import com.petsy.pets.domain.Pet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pets", path = "pets")
public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
}
