package com.Polo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.Polo.model.Project;
import com.Polo.service.ProjectService;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor

public class ProjectController {
    private final ProjectService projectService;

    // crear proyectos
    @PostMapping("/create")
    public void createProject(@RequestBody Project project) {
        projectService.createProject(project);
    }

    // eliminar proyectos
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable int id) {
        boolean isDeleted = projectService.deleteProject(id);
        if (isDeleted) {
            return ResponseEntity.ok("Proyecto eliminado existosamente");
        } else {
            return ResponseEntity.status(404).body("Proyecto no encontrado");
        }
    }

    // buscar todos los proyectos
    @GetMapping("/search")
    public List<Project> findAllProjects() {
        return projectService.findAllProjects();
    }

    // buscar proyecto por id
    @GetMapping("/search/{id}")
    public Optional<Project> findByProjectId(@PathVariable int id) {
        Optional<Project> project = projectService.findByProjectId(id);
        if (project.isPresent()) {
            return project;
        } else {
            return null;
        }
    }

    // buscar proyecto por nombre
    @GetMapping("/search/{projName}")
    public ResponseEntity<Project> findByProjectName(@PathVariable String projName) {
        Optional<Project> project = projectService.findByProjectName(projName);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }
}
