package com.project.challenge.application.usecases;

import com.project.challenge.application.adapter.HeroQueryService;
import com.project.challenge.application.dto.HeroDTO;
import com.project.challenge.application.exceptions.HeroNotFound;
import com.project.challenge.application.mapper.HeroMapper;
import com.project.challenge.domain.entity.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindHeroService {

    @Autowired
    private HeroQueryService heroQueryService;

    @Autowired
    private HeroMapper heroMapper;

    public HeroDTO findHero(Integer id) throws HeroNotFound {
        Hero hero = heroQueryService.findHero(id).orElseThrow(HeroNotFound::new);
        return heroMapper.toDTO(hero);
    }

}
