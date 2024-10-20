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

    public void createProject(Project project) {
        if (project != null) {
            projectRepository.save(project);
        } else {
            System.out.println("Error al crear el proyecto");
        }
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> findProjectById(int id) {
        return projectRepository.findById(id);
    }

    // public Project updateProject(Integer id, Project projectDetails) {
    // Project project = projectRepository.findById(id).orElseThrow();
    // project.setProjectName(projectDetails.getProjectName());
    // project.setProjectDescription(projectDetails.getProjectDescription());
    // project.setProjectStartDate(projectDetails.getProjectStartDate());
    // project.setProjectEndDate(projectDetails.getProjectEndDate());
    // return projectRepository.save(project);
    // }

    public boolean deleteProject(int id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
