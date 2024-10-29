package com.Polo.model;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EnvironmentVinculationMapper {
    EnvironmentVinculationDTO environmentVinculationToEnvironmentVinculationDTO(EnvironmentVinculation env);
    EnvironmentVinculation environmentVinculationDTOToEnvironmentVinculation(EnvironmentVinculationDTO envDTO);
    List<EnvironmentVinculationDTO> environmentVinculationListToEnvironmentVinculationDTOList(List<EnvironmentVinculation> envList);
    List<EnvironmentVinculation> environmentVinculationDTOListToEnvironmentVinculationList(List<EnvironmentVinculationDTO> envDTOs);
}
