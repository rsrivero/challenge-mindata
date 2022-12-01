package com.project.challenge.infrastructure.rest.controller;

import com.project.challenge.application.dto.HeroDTO;
import com.project.challenge.application.mapper.HeroMapper;
import com.project.challenge.application.usecases.SaveHeroService;
import com.project.challenge.infrastructure.rest.request.HeroDTORequest;
import com.project.challenge.infrastructure.rest.response.HeroDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/hero")
public class HeroController {

    @Autowired
    private SaveHeroService saveHeroService;

    @Autowired
    private HeroMapper heroMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public HeroDTOResponse create(@RequestBody HeroDTORequest req){
        HeroDTO hero = saveHeroService.save(req);
        return heroMapper.toResponse(hero);
    }

}
