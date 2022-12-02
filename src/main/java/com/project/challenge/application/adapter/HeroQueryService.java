package com.project.challenge.application.adapter;

import com.project.challenge.domain.entity.Hero;
import java.util.Optional;

public interface HeroQueryService {

    Optional<Hero> findHero(Integer id);

}
