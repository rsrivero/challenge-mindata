package com.project.challenge.application.usecases;

import com.project.challenge.application.adapter.HeroCommandService;
import com.project.challenge.application.adapter.HeroQueryService;
import com.project.challenge.application.dto.HeroDTO;
import com.project.challenge.application.exceptions.HeroNotFound;
import com.project.challenge.application.mapper.HeroMapper;
import com.project.challenge.domain.entity.Hero;
import com.project.challenge.infrastructure.rest.request.HeroDTORequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateHeroService {

    @Autowired
    private HeroCommandService heroCommandService;

    @Autowired
    private HeroQueryService heroQueryService;

    @Autowired
    private HeroMapper heroMapper;

    /**
     * Modify a superhero given his ID.
     * Update superhero name and strength with submitted data
     * Throw an exception if the superhero is not found
     *
     * @param id
     * @param heroRequest HeroDTORequest
     * @return HeroDTO
     * @throws com.project.challenge.application.exceptions.HeroNotFound
     */
    @CachePut(value = "heroes", key = "#id")
    @Transactional(propagation = Propagation.REQUIRED)
    public HeroDTO update(Integer id, HeroDTORequest heroRequest) throws HeroNotFound {
        Hero hero = heroQueryService.findHero(id).orElseThrow(HeroNotFound::new);
        var heroUpdated = heroMapper.updateEntity(hero, heroRequest);
        heroUpdated = heroCommandService.update(heroUpdated);
        return heroMapper.toDTO(heroUpdated);
    }
}
