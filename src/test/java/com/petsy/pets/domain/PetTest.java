package com.petsy.pets.domain;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Test
public class PetTest {

    private Pet fixture;

    @BeforeMethod
    public void setUp() {
        fixture = new Pet();
    }

    public void nameIsEmpty_isValid_returnsFalse() {
        fixture.setName(StringUtils.EMPTY);
        assertThat(fixture.isValid()).isFalse();
    }

    public void nameIsNull_isValid_returnsFalse() {
        fixture.setName(null);
        assertThat(fixture.isValid()).isFalse();
    }

    public void nameHasContent_isValid_returnsTrue() {
        fixture.setName("A name");
        assertThat(fixture.isValid()).isTrue();
    }
}
