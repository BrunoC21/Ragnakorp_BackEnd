package com.Polo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.Polo.model.*;
import com.Polo.service.ProjectService;
import com.Polo.service.UserService;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor

public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    private final ProjectMapper projectMapper;

    // crear proyectos
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody ProjectDTO projectDTO) {

        Project project = projectMapper.projectDTOToProject(projectDTO);

        boolean chek = projectService.createProject(project);
        if (chek) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Proyecto creado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Proyecto no creado");
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
    public ResponseEntity<List<ProjectDTO>> findAllProjects() {
        List<ProjectDTO> projectDTOList = projectService.findAllProjects();
        if (!projectDTOList.isEmpty()) {
            return new ResponseEntity<>(projectDTOList, HttpStatus.OK);
        } else { 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar proyecto por id
    @GetMapping("/search/{id}")
    public ResponseEntity<ProjectDTO> findByProjectId(@PathVariable int id) {
        Optional<ProjectDTO> projectDTO = projectService.findByProjectId(id);
        if (projectDTO.isPresent()) {
            return new ResponseEntity<>(projectDTO.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar proyecto por nombre
    @GetMapping("/search/name/{projName}")
    public ResponseEntity<ProjectDTO> findByProjName(@PathVariable String projName) {
        Optional<ProjectDTO> project = projectService.findByProjName(projName);
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
