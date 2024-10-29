package com.Polo.service;

import com.Polo.model.*;
import com.Polo.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

    public boolean createProject(Project project) {
        if (project != null) {
            Optional<Project> existingProject = projectRepository.findByProjName(project.getProjName());
            if (existingProject != null) {
                System.out.println("El proyecto ya existe.");
                return false;
            } else {
                projectRepository.save(project);
                System.out.println("Proyecto creado exitosamente.");
                return true;
            }
        }
        return false;
    }

    public List<ProjectDTO> findAllProjects() {
        List<Project> projectList = projectRepository.findAll();
        List<ProjectDTO> projectDTOList;
        projectDTOList = mapper.projectListToProjectDTOList(projectList);
        return projectDTOList;
    }

    public Optional<ProjectDTO> findByProjectId(int id) {
        Optional<Project> optional = projectRepository.findById(id);
        if (optional.isPresent()) {
            return Optional.of(mapper.projectToProjectDTO(optional.get()));
        }
        return Optional.empty();
    }

    // busqueda de projecto por nombre
    public Optional<ProjectDTO> findByProjName(String projName) {
        Optional<Project> optional = projectRepository.findByProjName(projName);
        if (optional.isPresent()) {
            return Optional.of(mapper.projectToProjectDTO(optional.get()));
        }
        return Optional.empty();
    }

    public boolean deleteProject(int id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
