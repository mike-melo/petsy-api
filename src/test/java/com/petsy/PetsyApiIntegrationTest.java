package com.petsy;

import com.petsy.pets.domain.Pet;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.fail;

@Test
public class PetsyApiIntegrationTest extends BaseIntegrationTest {

    private List<Link> linksToPetsAdded = new ArrayList<>();

    public void getAllPets() {
        Pet dog = makePetWith("Doggie");
        Pet cat = makePetWith("Kitty");

        addPet(dog);
        addPet(cat);

        List<Pet> pets = getPets();

        assertThat(pets.size()).isEqualTo(2);
        assertThat(pets.get(0)).isEqualTo(dog);
        assertThat(pets.get(1)).isEqualTo(cat);
    }

    public void addAndGetAPet() {
        Pet petAdded = makePetWith("Doggie");

        Link linkToPostedPet = addPet(petAdded);
        Pet petRetrieved = getPet(linkToPostedPet);

        assertThat(petRetrieved).isEqualTo(petAdded);
    }

    public void addAndRemoveAPet() {
        Pet petAdded = makePetWith("Doggie");
        petAdded.setName("Doggie");

        Link linkToPostedPet = addPet(petAdded);
        deletePet(linkToPostedPet);

        try {
            getPet(linkToPostedPet);
            fail("An HttpClientErrorException should have been thrown with an HTTP 404 Not Found status code");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    private Pet makePetWith(String name) {
        Pet pet = new Pet();
        pet.setName(name);
        return pet;
    }

    private Link addPet(Pet pet) {
        ResponseEntity<Resource<Pet>> postResponseEntity =
                getRestTemplate().exchange(getBaseUrl(), HttpMethod.POST, new HttpEntity<>(pet), new ParameterizedTypeReference<Resource<Pet>>() {
                }, Collections.emptyMap());

        Link linkToAddedPet = postResponseEntity.getBody().getLinks().get(0);
        linksToPetsAdded.add(linkToAddedPet);

        return linkToAddedPet;
    }

    private void deletePet(Link link) {
        getRestTemplate().exchange(link.getHref(), HttpMethod.DELETE, null, new ParameterizedTypeReference<Resource<Pet>>() {
        }, Collections.emptyMap());
    }

    private Pet getPet(Link link) {
        ResponseEntity<Resource<Pet>> responseEntity =
                getRestTemplate().exchange(link.getHref(), HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Pet>>() {
                }, Collections.emptyMap());

        Resource<Pet> petResource = responseEntity.getBody();
        return petResource.getContent();
    }

    private ArrayList<Pet> getPets() {
        ResponseEntity<Resources<Pet>> responseEntity =
                getRestTemplate().exchange(getBaseUrl(), HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Pet>>() {
                }, Collections.emptyMap());

        Resources<Pet> petResource = responseEntity.getBody();
        return new ArrayList<>(petResource.getContent());
    }

    @BeforeMethod
    public void defensivelyRemoveAllPets() {
        linksToPetsAdded.parallelStream().forEach(link -> {
            try {
                deletePet(link);
            } catch (HttpClientErrorException e) {
                //If the pet isn't found, it was probably removed in the test.
                //If there's a different error code, re-throw it as it means
                //something unexpected happened.
                if (!HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                    throw e;
                }
            }
        });
        linksToPetsAdded.clear();
    }
}
