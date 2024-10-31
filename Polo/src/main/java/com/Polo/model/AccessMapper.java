package com.Polo.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccessMapper {

    @Mapping(target = "stadisticsIds", source = "stadistics", qualifiedByName = "mapStadisticsToIds")
    AccessDTO toDto(Access access);

    @Mapping(target = "stadistics", ignore = true) // Ignora la lista al mapear de vuelta para evitar ciclo
    Access toEntity(AccessDTO accessDTO);

    @Named("mapStadisticsToIds")
    default List<Integer> mapStadisticsToIds(List<Stadistics> stadistics) {
        return stadistics.stream()
                .map(Stadistics::getId)
                .collect(Collectors.toList());
    }
}
