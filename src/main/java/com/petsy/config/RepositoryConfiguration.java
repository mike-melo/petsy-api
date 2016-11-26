package com.petsy.config;

import com.petsy.pets.events.PetEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    PetEventHandler personEventHandler() {
        return new PetEventHandler();
    }

}