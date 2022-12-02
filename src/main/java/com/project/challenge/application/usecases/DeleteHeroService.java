package com.project.challenge.application.usecases;

import com.project.challenge.application.adapter.HeroCommandService;
import com.project.challenge.application.adapter.HeroQueryService;
import com.project.challenge.application.exceptions.HeroNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteHeroService {

    @Autowired
    private HeroCommandService heroCommandService;

    @Autowired
    private HeroQueryService heroQueryService;

    /**
     * Eliminate a superhero given his ID
     * Throw an exception if the superhero is not found
     *
     * @param id
     * @return void
     * @throws com.project.challenge.application.exceptions.HeroNotFound
     */
    @CacheEvict(value = "heroes", key = "#id")
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) throws HeroNotFound {
        heroQueryService.findHero(id).orElseThrow(HeroNotFound::new);
        heroCommandService.delete(id);
    }
}
