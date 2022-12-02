package com.project.challenge.application.adapter;

import com.project.challenge.domain.entity.Hero;


public interface HeroCommandService {

    Hero save(Hero hero);

    Hero update(Hero hero);

    void delete(Integer id);
}
