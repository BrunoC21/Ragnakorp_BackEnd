package com.Polo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.Polo.model.*;
import com.Polo.service.EnvironmentVinculationService;
import com.Polo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/environmentVinculation")
@RequiredArgsConstructor
@CrossOrigin("http://127.0.0.1:5500")
public class EnvironmentVinculationController {
    private final EnvironmentVinculationService environmentVinculationService;
    private final UserService userService;

    private final EnvironmentVinculationMapper environmentVinculationMapper;

    // eliminar actividades
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable int id) {
        boolean isDeleted = environmentVinculationService.deleteActivity(id);
        if (isDeleted) {
            return ResponseEntity.ok("Actividad eliminada existosamente");
        } else {
            return ResponseEntity.status(404).body("Actividad no encontrada");
        }
    }

    // buscar todas las actividades
    @GetMapping("/search")
    public ResponseEntity<List<EnvironmentVinculationDTO>> findAllActivities() {
        List<EnvironmentVinculationDTO> environmentVinculationDTOList = environmentVinculationService.findAllActivities();
        if (!environmentVinculationDTOList.isEmpty()) {
            return new ResponseEntity<>(environmentVinculationDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar actividad por id
    @GetMapping("/search/{id}")
    public ResponseEntity<EnvironmentVinculationDTO> findByActivityId(@PathVariable int id) {
        Optional<EnvironmentVinculationDTO> environmentVinculationDTO = environmentVinculationService
                .findByActivityId(id);
        if (environmentVinculationDTO.isPresent()) {
            return new ResponseEntity<>(environmentVinculationDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar actividad por nombre
    @GetMapping("/search/name/{activityName}")
    public ResponseEntity<EnvironmentVinculationDTO> findByActivityName(@PathVariable String activityName) {
        Optional<EnvironmentVinculationDTO> environmentVinculationDTO = environmentVinculationService
                .findByActivityName(activityName);
        return environmentVinculationDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // Crear actividades poor administrativos
    @PostMapping("/create")
    public ResponseEntity<String> createActivityByAdministrativeName(@RequestBody Map<String, Object> session) {
        
        // Crear una instancia de ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

         // extraer datos de sesion
        @SuppressWarnings("unchecked") // para evitar excepciones inesperadas
        Map<String, Object> sessionData = (Map<String, Object>) session.get("sessionData");
        EnvironmentVinculationDTO environmentVinculationDTO = objectMapper.convertValue(session.get("vinculacionDTO"), EnvironmentVinculationDTO.class);

        String role = sessionData.get("role").toString();
        String userRut = sessionData.get("userRut").toString();

        if ("ADMINISTRATIVE".equals(role)) {
            int id = userService.findUserByRut(userRut).get().getId();
            EnvironmentVinculation environmentVinculation = environmentVinculationMapper
                    .environmentVinculationDTOToEnvironmentVinculation(environmentVinculationDTO);
            
            boolean chek = environmentVinculationService.createActivity(environmentVinculation, id);
            
            if (chek) {
                return ResponseEntity.ok("Actividad creada exitosamente");
            } else {
                return ResponseEntity.status(400).body("Actividad no creada");
            }
        } else {
            return ResponseEntity.status(401).body("Usuario sin permisos");
        }
    }

    // metodo para modificar las actividades de vinculacion con el medio
    @PutMapping("/update")
    public ResponseEntity<String> updateEnvironmentVinculation(@RequestBody Map<String, Object> payload) {

        try {
            // Crear una instancia de ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Extraer los datos de la sesión y de la noticia
            @SuppressWarnings("unchecked")
            Map<String, Object> sessionData = (Map<String, Object>) payload.get("sessionData");
            EnvironmentVinculationDTO environmentVinculationDTO = objectMapper.convertValue(payload.get("vinculation"), EnvironmentVinculationDTO.class);

            // Validar sesión
            String role = sessionData.get("role").toString();
            if (!"ADMINISTRATIVE".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no tiene el rol necesario");
            }

            boolean updated = environmentVinculationService.updateEnvironmentVinculation(environmentVinculationDTO.getId(), environmentVinculationDTO);

            if (updated) {
                return ResponseEntity.status(HttpStatus.OK).body("Vinculacion actualizada");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar la vinculacion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

}