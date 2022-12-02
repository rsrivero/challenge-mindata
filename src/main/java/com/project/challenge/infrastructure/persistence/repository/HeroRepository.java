package com.project.challenge.infrastructure.persistence.repository;

import com.project.challenge.application.adapter.HeroCommandService;
import com.project.challenge.application.adapter.HeroQueryService;
import com.project.challenge.domain.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Integer>,
        JpaSpecificationExecutor<Hero>,
        HeroCommandService,
        HeroQueryService {

    default Optional<Hero> findHero(Integer id) {
        return this.findById(id);
    }
    default void delete(Integer id) {
        this.deleteById(id);
    }
}
