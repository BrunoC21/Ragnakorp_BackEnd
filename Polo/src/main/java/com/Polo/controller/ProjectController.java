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
import com.Polo.service.UserService;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor

public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    // crear proyectos
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Project project) {
        boolean chek = projectService.createProject(project);
        if (chek) {
            return ResponseEntity.ok("Proyecto creado exitosamente");
        } else {
            return ResponseEntity.status(404).body("Proyecto no creado");
        }
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
    @GetMapping("/search/name/{projName}")
    public ResponseEntity<Project> findByProjName(@PathVariable String projName) {
        Optional<Project> project = projectService.findByProjName(projName);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @PostMapping("/create/{administrativeName}/{userRut}")
    public ResponseEntity<String> createProjectByAdministrativeName(
            @PathVariable String administrativeName,
            @PathVariable String userRut,
            @RequestBody Project project) {

        if (!userService.isAdministrative(administrativeName, userRut)) {
            return ResponseEntity.status(403).body("No tienes permisos para crear un proyecto");
        }

        boolean chek = projectService.createProject(project);
        if (chek) {
            return ResponseEntity.ok("Proyecto creado exitosamente");
        } else {
            return ResponseEntity.status(400).body("Proyecto no creado");
        }
    }
}
