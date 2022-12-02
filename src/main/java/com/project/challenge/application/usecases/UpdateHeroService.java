package com.project.challenge.application.usecases;

import com.project.challenge.application.adapter.HeroCommandService;
import com.project.challenge.application.adapter.HeroQueryService;
import com.project.challenge.application.dto.HeroDTO;
import com.project.challenge.application.exceptions.HeroNotFound;
import com.project.challenge.application.mapper.HeroMapper;
import com.project.challenge.domain.entity.Hero;
import com.project.challenge.infrastructure.rest.request.HeroDTORequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional(propagation = Propagation.REQUIRED)
    public HeroDTO update(Integer id, HeroDTORequest heroRequest) throws HeroNotFound {
        Hero hero = heroQueryService.findHero(id).orElseThrow(HeroNotFound::new);
        var heroUpdated = heroMapper.updateEntity(hero, heroRequest);
        heroUpdated = heroCommandService.update(heroUpdated);
        return heroMapper.toDTO(heroUpdated);
    }
}
