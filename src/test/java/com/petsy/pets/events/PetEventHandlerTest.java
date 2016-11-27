package com.petsy.pets.events;

import com.petsy.exceptions.ValidationException;
import com.petsy.pets.domain.Pet;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

@Test
public class PetEventHandlerTest {

    private PetEventHandler fixture;

    @Mock
    private Pet pet;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fixture = new PetEventHandler();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void invalidPet_handleBeforeCreate_shouldThrowAValidationException() {
        when(pet.isValid()).thenReturn(false);
        fixture.handleBeforeCreate(pet);
    }

    public void validPet_handleBeforeCreate_shouldNotThrowAnException() {
        when(pet.isValid()).thenReturn(true);
        try {
            fixture.handleBeforeCreate(pet);
        } catch (ValidationException e) {
            fail("A ValidationException should not have been thrown");
        }
    }

}
