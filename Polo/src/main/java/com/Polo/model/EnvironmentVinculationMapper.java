package com.Polo.model;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnvironmentVinculationMapper {
    List<EnvironmentVinculationDTO> environmentVinculationListToEnvironmentVinculationDTOList(List<EnvironmentVinculation> entities);
    EnvironmentVinculationDTO environmentVinculationToEnvironmentVinculationDTO(EnvironmentVinculation entity);
    EnvironmentVinculation environmentVinculationDTOToEnvironmentVinculation(EnvironmentVinculationDTO dto);
}