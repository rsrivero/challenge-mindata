package com.project.challenge.infrastructure.persistence.repository;

import com.project.challenge.application.adapter.HeroCommandService;
import com.project.challenge.domain.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Integer>,
        JpaSpecificationExecutor<Hero>,
        HeroCommandService {
}
