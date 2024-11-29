package com.Polo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.model.Project;
import com.Polo.model.ProjectDTO;
import com.Polo.model.ProjectMapper;
import com.Polo.model.UserDTO;
import com.Polo.service.ProjectService;
import com.Polo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
@CrossOrigin("http://127.0.0.1:5500")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectMapper projectMapper;

    // eliminar proyectos, si bien esta creado, no se permite utilizar en un principio, ya que al hacer postulaciones a los proyectos, estos guardan su pk en una 3era tabla que nace de la postulacion al proyecto, por lo cual para eliminarla se requiere una mayor logica.
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
            return new ResponseEntity<>(projectDTO.get(), HttpStatus.OK);
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

    @PostMapping("/create")
    public ResponseEntity<String> createProjectByAdministrativeName(@RequestBody Map<String, Object> session) {

        // Crear una instancia de ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // extraer datos de sesion
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionData = (Map<String, Object>) session.get("sessionData");
        ProjectDTO projectDTO = objectMapper.convertValue(session.get("projectData"), ProjectDTO.class);

        Project project = projectMapper.projectDTOToProject(projectDTO);

        String role = sessionData.get("role").toString();
        String rut = sessionData.get("userRut").toString();

        Optional<UserDTO> userDTO = userService.findUserByRut(rut);

        if ("INVESTIGADOR".equals(role) || "ADMINISTRATIVE".equals(role) && userDTO.isPresent()) {
            boolean chek = projectService.createProject(project, rut);
            if (chek) {
                return ResponseEntity.ok("Proyecto creado exitosamente");
            } else {
                return ResponseEntity.status(400).body("Proyecto no creado");
            }
        } else {
            return ResponseEntity.status(400).body("El usuario no tiene los permisos necesarios");
        }

    }

}
