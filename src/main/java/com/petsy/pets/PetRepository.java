package com.petsy.pets;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pet", path = "pet")
public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
}
