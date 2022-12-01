package com.project.challenge.application.mapper;

import com.project.challenge.application.dto.HeroDTO;
import com.project.challenge.domain.entity.Hero;
import com.project.challenge.infrastructure.rest.request.HeroDTORequest;
import com.project.challenge.infrastructure.rest.response.HeroDTOResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeroMapper {

    Hero toEntity(HeroDTORequest request);

    HeroDTO toDTO(Hero hero);

    HeroDTOResponse toResponse(HeroDTO hero);
}
