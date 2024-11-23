package com.Polo.model;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EnvironmentVinculationMapper {
        EnvironmentVinculationDTO environmentVinculationToEnvironmentVinculationDTO(
                        EnvironmentVinculation environmentVinculation);

        EnvironmentVinculation environmentVinculationDTOToEnvironmentVinculation(
                        EnvironmentVinculationDTO environmentVinculationDTO);

        List<EnvironmentVinculationDTO> environmentVinculationListToEnvironmentVinculationDTOList(
                        List<EnvironmentVinculation> environmentVinculations);

}
