package com.Polo.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuscriptionsMapper {
    SuscriptionsDTO toDto(Suscription suscriptions);
    Suscription toEntity(SuscriptionsDTO suscriptionsDTO);
}
