package com.project.challenge.service;

import com.github.javafaker.Faker;
import com.project.challenge.infrastructure.rest.request.HeroDTORequest;
import org.springframework.stereotype.Service;

@Service
public class HeroeRequestBuilder {

    private final Faker faker = new Faker();

    public HeroDTORequest build() {
        return HeroDTORequest.builder()
                .name(faker.superhero().name())
                .power(faker.superhero().power())
                .build();

    }
}
