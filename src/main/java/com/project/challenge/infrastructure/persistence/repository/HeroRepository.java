package com.project.challenge.infrastructure.persistence.repository;

import com.project.challenge.application.adapter.HeroCommandService;
import com.project.challenge.application.adapter.HeroQueryService;
import com.project.challenge.domain.entity.Hero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
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

    default Hero update(Hero hero) {
        return ((CrudRepository<Hero, Integer>) this).save(hero);
    }

    default Page<Hero> findAllPaged(Pageable pageable, Specification<Hero> where) {
        return this.findAll(where, pageable);
    }

}
