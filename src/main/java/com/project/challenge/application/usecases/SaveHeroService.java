package com.project.challenge.application.usecases;

import com.project.challenge.application.adapter.HeroCommandService;
import com.project.challenge.application.dto.HeroDTO;
import com.project.challenge.application.mapper.HeroMapper;
import com.project.challenge.infrastructure.rest.request.HeroDTORequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SaveHeroService {

    @Autowired
    private HeroCommandService heroCommandService;

    @Autowired
    private HeroMapper heroMapper;

    /**
     * Save a hero.
     *
     * @param request HeroDTORequest
     * @return saved hero
     */
    public HeroDTO save(HeroDTORequest request) {
        var hero = heroMapper.toEntity(request);
        hero = heroCommandService.save(hero);
        return heroMapper.toDTO(hero);
    }
}
