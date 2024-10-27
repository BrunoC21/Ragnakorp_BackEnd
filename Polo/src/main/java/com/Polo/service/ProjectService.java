package com.Polo.service;

import com.Polo.model.Project;
import com.Polo.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public boolean createProject(Project project) {
        if (project != null) {
            Project existingProject = projectRepository.findByProjName(project.getProjName());
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

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> findByProjectId(int id) {
        return projectRepository.findById(id);
    }

    // busqueda de projecto por nombre
    public Optional<Project> findByProjName(String projName) {
        return Optional.ofNullable(projectRepository.findByProjName(projName));
    }

    public boolean deleteProject(int id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
