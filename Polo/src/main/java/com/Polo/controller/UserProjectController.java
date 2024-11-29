package com.Polo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.model.PostulationDTO;
import com.Polo.model.ProjectDTO;
import com.Polo.service.PostulationService;
import com.Polo.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/userProj")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class UserProjectController {
    @Autowired
    private PostulationService postulationService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/myproj")
    public ResponseEntity<List<PostulationDTO>> getProjectsByUserSession(@RequestBody Map<String, Object> sessionData) {
        try {

            // Extraer los datos de la sesión
            @SuppressWarnings("unchecked")
            Map<String, Object> session = (Map<String, Object>) sessionData.get("sessionData");
            if (session == null || !session.containsKey("userRut")) {
                return ResponseEntity.badRequest().body(null);
            }
            // Obtener el RUT del usuario desde los datos de sesión
            String userRut = session.get("userRut").toString();

            // Obtener las postulaciones del usuario como DTO
            List<PostulationDTO> postulations = postulationService.getPostulationsByUser(userRut);

            if (!postulations.isEmpty()) {
                System.out.println("SE RECUPERARON LAS POSTULACIONES: " + postulations);
                return ResponseEntity.ok(postulations);
            } else {
                System.out.println("NO SE RECUPERÓ NADA");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/myprojCreate")
    public ResponseEntity<List<ProjectDTO>> getProjectsCreatedByUserSession(@RequestBody Map<String, Object> sessionData) {
        try {
            // Extraer los datos de la sesión
            @SuppressWarnings("unchecked")
            Map<String, Object> session = (Map<String, Object>) sessionData.get("sessionData");
            if (session == null || !session.containsKey("userRut")) {
                return ResponseEntity.badRequest().body(null);
            }

            // Obtener el RUT del usuario desde los datos de sesión
            String userRut = session.get("userRut").toString();
            // Obtener las postulaciones del usuario como DTO
            List<ProjectDTO> projects = projectService.getProjectsCreatedByUser(userRut);

            if (!projects.isEmpty()) {
                System.out.println("SE RECUPERARON LOS PROYECTOS: " + projects);
                return ResponseEntity.ok(projects);
            } else {
                System.out.println("NO SE RECUPERÓ NADA");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    
}
