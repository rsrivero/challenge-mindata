package com.project.challenge.infrastructure.rest.controller;

import com.project.challenge.application.annotations.TimerLog;
import com.project.challenge.application.dto.HeroDTO;
import com.project.challenge.application.exceptions.HeroNotFound;
import com.project.challenge.application.mapper.HeroMapper;
import com.project.challenge.application.usecases.DeleteHeroService;
import com.project.challenge.application.usecases.FindHeroService;
import com.project.challenge.application.usecases.SaveHeroService;
import com.project.challenge.application.usecases.UpdateHeroService;
import com.project.challenge.domain.entity.Hero;
import com.project.challenge.infrastructure.rest.request.HeroDTORequest;
import com.project.challenge.infrastructure.rest.response.HeroDTOResponse;
import com.sipios.springsearch.anotation.SearchSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/hero")
public class HeroController {

    @Autowired
    private SaveHeroService saveHeroService;

    @Autowired
    private UpdateHeroService heroUpdaterService;

    @Autowired
    private FindHeroService heroFinderService;

    @Autowired
    private HeroMapper heroMapper;

    @Autowired
    private DeleteHeroService heroEliminatorService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @TimerLog
    public HeroDTOResponse create(@RequestBody HeroDTORequest req){
        HeroDTO hero = saveHeroService.save(req);
        return heroMapper.toResponse(hero);
    }

    @GetMapping("/{id}")
    @TimerLog
    public HeroDTOResponse find(@PathVariable Integer id) throws HeroNotFound {
        HeroDTO hero = heroFinderService.findHero(id);
        return heroMapper.toResponse(hero);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @TimerLog
    public void delete(@PathVariable Integer id) throws HeroNotFound {

        heroEliminatorService.delete(id);

    }

    @PutMapping(value = "/{id}",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @TimerLog
    public HeroDTOResponse update(@RequestBody HeroDTORequest req, @PathVariable Integer id) throws HeroNotFound {

        HeroDTO hero = heroUpdaterService.update(id, req);

        return heroMapper.toResponse(hero);
    }

    @GetMapping()
    @TimerLog
    public Page<HeroDTOResponse> index(Pageable pageable, @SearchSpec Specification<Hero> specs) throws HeroNotFound {

        Page<HeroDTO> heroes = heroFinderService.findAll(pageable, Specification.where(specs));

        return heroes.map(heroMapper::toResponse);
    }

}
