package com.Polo.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PoloCenterMapper {
    PoloCenterDTO toDto(Polocenter poloCenter);
    Polocenter toEntity(PoloCenterDTO poloCenterDTO);
}

