package com.Polo.model;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostulationMapper {
    PostulationDTO postulationToPostulationDTO(Postulation postulation);
    Postulation postulationDTOToPostulation(PostulationDTO postulationDTO);
    List<PostulationDTO> postulationListToPostulationDTOList(List<Postulation> postulations);
    List<Postulation> postulationDTOListToPostulationList(List<PostulationDTO> postulationDTOs);
}
