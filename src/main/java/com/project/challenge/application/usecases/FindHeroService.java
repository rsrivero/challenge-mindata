package com.project.challenge.application.usecases;

import com.project.challenge.application.adapter.HeroQueryService;
import com.project.challenge.application.dto.HeroDTO;
import com.project.challenge.application.exceptions.HeroNotFound;
import com.project.challenge.application.mapper.HeroMapper;
import com.project.challenge.domain.entity.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FindHeroService {

    @Autowired
    private HeroQueryService heroQueryService;

    @Autowired
    private HeroMapper heroMapper;

    /**
     * Superhero Search by ID Throw an exception if not found.
     * The query result is cached.
     * Throw an exception if the superhero is not found
     *
     * @param id Integer
     * @return HeroDTO
     * @throws com.project.challenge.application.exceptions.HeroNotFound
     */
    @Cacheable( value = "heroes")
    public HeroDTO findHero(Integer id) throws HeroNotFound {
        Hero hero = heroQueryService.findHero(id).orElseThrow(HeroNotFound::new);
        return heroMapper.toDTO(hero);
    }

    /**
     * Superhero Search with Specification
     *
     * @param pageable
     * @param where
     * @return Page<HeroDTO>
     */
    public Page<HeroDTO> findAll(Pageable pageable, Specification<Hero> where) {
        Page<Hero> heroes = heroQueryService.findAllPaged(pageable, where);
        return  heroes.map(heroMapper::toDTO);
    }
}
