package com.project.challenge.controller.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.project.challenge.service.HeroFactory;
import com.project.challenge.service.HeroeRequestBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HeroCrudControllerTest {

    @Autowired
    private HeroeRequestBuilder heroeRequestBuilder;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private HeroFactory heroFactory;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String heroPath = "/v1/hero";

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void test_Create_Should_Create_When_CreateHero() throws Exception {
        var req = heroeRequestBuilder.build();
        this.mockMvc.perform(
                post(heroPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", equalTo(req.getName())))
                .andExpect(jsonPath("$.power", equalTo(req.getPower())));

    }

    @Test
    public void test_Find_Should_FindHero_When_HeroIdFound() throws Exception {
        var hero = heroFactory.create();
        var heroFindId = heroPath.concat("/").concat(String.valueOf(hero.getId()));

        this.mockMvc.perform(
                        get(heroFindId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(hero.getId())))
                .andExpect(jsonPath("$.name", equalTo(hero.getName())))
                .andExpect(jsonPath("$.power", equalTo(hero.getPower())));

    }

    @Test
    public void test_Find_Should_NotFount_When_HeroIdNotFound() throws Exception {
        var heroFindId = heroPath.concat("/")
                .concat("498498494");

        this.mockMvc.perform(
                        get(heroFindId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Delete_Should_Delete_When_HeroIdFound() throws Exception {
        var hero = heroFactory.create();

        var heroFindId = heroPath.concat("/")
                .concat(String.valueOf(hero.getId()));

        this.mockMvc.perform(
                        delete(heroFindId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_update_Should_update_When_HeroIdFound() throws Exception {
        var hero = heroFactory.create();

        var heroFindId = heroPath.concat("/")
                .concat(String.valueOf(hero.getId()));

        hero.setName(new Faker().superhero().name());

        this.mockMvc.perform(
                        put(heroFindId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(hero)))
                .andDo(print())
                .andExpect( status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", equalTo(hero.getName())))
                .andExpect(jsonPath("$.power", equalTo(hero.getPower())));
    }

    @Test
    public void test_Index_Should_FindAll_When_HeroesFounds() throws Exception {
        var firstHero = heroFactory.create();
        var secondHero = heroFactory.create();

        this.mockMvc.perform(
                        get(heroPath)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].id", notNullValue()))
                .andExpect(jsonPath("$.content.[0].name", notNullValue()))
                .andExpect(jsonPath("$.content.[0].power", notNullValue()))
                .andExpect(jsonPath("$.content.[1].id", notNullValue()))
                .andExpect(jsonPath("$.content.[1].name", notNullValue()))
                .andExpect(jsonPath("$.content.[1].power", notNullValue()));

    }


}
