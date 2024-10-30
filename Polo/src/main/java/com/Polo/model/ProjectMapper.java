package com.Polo.model;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = { ChangesMapper.class, PostulationMapper.class })
public interface ProjectMapper {
    ProjectDTO projectToProjectDTO(Project project);
    Project projectDTOToProject(ProjectDTO projectDTO);
    List<ProjectDTO> projectListToProjectDTOList(List<Project> projects);
    List<Project> projectDTOListToProjectList(List<ProjectDTO> projectDTOs);
}

