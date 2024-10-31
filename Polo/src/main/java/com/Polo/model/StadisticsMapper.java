package com.Polo.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StadisticsMapper {
    StadisticsDTO toDto(Stadistics stadistics);
    Stadistics toEntity(StadisticsDTO stadisticsDTO);
}

