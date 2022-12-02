package com.project.challenge.service;

import com.project.challenge.application.dto.HeroDTO;
import com.project.challenge.application.usecases.SaveHeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeroFactory {
    @Autowired
    private SaveHeroService saveHeroService;
    @Autowired
    private HeroeRequestBuilder heroeRequestBuilder;

    public HeroDTO create() {
        var req = heroeRequestBuilder.build();
        return saveHeroService.save(req);
    }
}
