package com.Polo.controller;

import java.util.List;
<<<<<<< HEAD
import java.util.Map;
=======

import java.util.Map;


>>>>>>> 15fb7ec6ee4be5f5027a92b39ad9db8c592b3906
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

import com.Polo.model.EnvironmentVinculation;
import com.Polo.model.EnvironmentVinculationDTO;
import com.Polo.model.EnvironmentVinculationMapper;
import com.Polo.service.EnvironmentVinculationService;
import com.Polo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/environmentVinculation")
@RequiredArgsConstructor
@CrossOrigin("http://127.0.0.1:5500")
public class EnvironmentVinculationController {

    private final EnvironmentVinculationService environmentVinculationService;
    private final UserService userService;

    private final EnvironmentVinculationMapper environmentVinculationMapper;

    // crear actividades
    // @PostMapping("/create")
    // public ResponseEntity<String> createActivity(@RequestBody
    // EnvironmentVinculationDTO environmentVinculationDTO) {
<<<<<<< HEAD
=======


    //     EnvironmentVinculation environmentVinculation = environmentVinculationMapper.environmentVinculationDTOToEnvironmentVinculation(environmentVinculationDTO);

    //     boolean chek = environmentVinculationService.createActivity(environmentVinculation, 25);
    //     if (chek) {
    //         return ResponseEntity.status(HttpStatus.CREATED).body("Actividad creadaexitosamente");
    //     } else {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Actividad no creada");
    //     }

>>>>>>> 15fb7ec6ee4be5f5027a92b39ad9db8c592b3906
    // EnvironmentVinculation environmentVinculation = environmentVinculationMapper
    // .environmentVinculationDTOToEnvironmentVinculation(environmentVinculationDTO);
    // boolean chek =
    // environmentVinculationService.createActivity(environmentVinculation);
    // if (chek) {
    // return ResponseEntity.status(HttpStatus.CREATED).body("Actividad creada
    // exitosamente");
    // } else {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Actividad no
    // creada");
    // }

    // }
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
        List<EnvironmentVinculationDTO> environmentVinculationDTOList = environmentVinculationService
                .findAllActivities();
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

<<<<<<< HEAD
    // @PostMapping("/create/{administrativeName}/{userRut}")
    // public ResponseEntity<String> createActivityByAdministrativeName(@PathVariable String administrativeName,
    //         @PathVariable String userRut, @RequestBody EnvironmentVinculationDTO environmentVinculationDTO) {
    //     if (!userService.isAdministrative(administrativeName, userRut)) {
    //         return ResponseEntity.status(403).body("No tienes permisos para crear la actividad");
    //     }
    //     int id = userService.findUserByRut(userRut).get().getId();
    //     EnvironmentVinculation environmentVinculation = environmentVinculationMapper
    //             .environmentVinculationDTOToEnvironmentVinculation(environmentVinculationDTO);
    //     boolean chek = environmentVinculationService.createActivity(environmentVinculation, id);
    //     if (chek) {
    //         return ResponseEntity.ok("Actividad creada exitosamente");
    //     } else {
    //         return ResponseEntity.status(400).body("Actividad no creada");
    //     }
    // }
    // Crear actividades poor administrativos
    @PostMapping("/create")
    public ResponseEntity<String> createActivityByAdministrativeName(
            @RequestBody EnvironmentVinculationDTO environmentVinculationDTO,
            @RequestBody Map<String, Object> session) {

        // extraer datos de sesion
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionData = (Map<String, Object>) session.get("sessionData");
=======

    // Crear actividades poor administrativos
    @PostMapping("/create")
    public ResponseEntity<String> createActivityByAdministrativeName(@RequestBody Map<String, Object> session) {
        
        // Crear una instancia de ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

         // extraer datos de sesion
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionData = (Map<String, Object>) session.get("sessionData");
        EnvironmentVinculationDTO environmentVinculationDTO = objectMapper.convertValue(session.get("environmentVinculation"), EnvironmentVinculationDTO.class);
>>>>>>> 15fb7ec6ee4be5f5027a92b39ad9db8c592b3906

        String role = sessionData.get("role").toString();
        String userRut = sessionData.get("userRut").toString();

        if ("ADMINISTRATIVE".equals(role)) {
            int id = userService.findUserByRut(userRut).get().getId();
            EnvironmentVinculation environmentVinculation = environmentVinculationMapper
                    .environmentVinculationDTOToEnvironmentVinculation(environmentVinculationDTO);
<<<<<<< HEAD

            boolean chek = environmentVinculationService.createActivity(environmentVinculation, id);

=======
            
            boolean chek = environmentVinculationService.createActivity(environmentVinculation, id);
            
>>>>>>> 15fb7ec6ee4be5f5027a92b39ad9db8c592b3906
            if (chek) {
                return ResponseEntity.ok("Actividad creada exitosamente");
            } else {
                return ResponseEntity.status(400).body("Actividad no creada");
            }
<<<<<<< HEAD
=======
        } else {
            return ResponseEntity.status(401).body("Usuario sin permisos");
        }
    }

    // metodo para modificar las actividades de vinculacion con el medio
    @PutMapping("/update")
    public ResponseEntity<EnvironmentVinculationDTO> updateEnvironmentVinculation(@RequestBody EnvironmentVinculationDTO dto, @RequestBody Map<String, Object> session) {

        // extraer datos de sesion
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionData = (Map<String, Object>) session.get("sessionData");

        // String role = sessionData.get("role").toString();
        String userRut = sessionData.get("userRut").toString();

        int id = userService.findUserByRut(userRut).get().getId();
        EnvironmentVinculationDTO updated = environmentVinculationService.updateEnvironmentVinculation(id, dto);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

    @PostMapping("/create/{administrativeName}/{userRut}")
    public ResponseEntity<String> createActivityByAdministrativeName(@PathVariable String administrativeName,
            @PathVariable String userRut, @RequestBody EnvironmentVinculationDTO environmentVinculationDTO) {
        if (!userService.isAdministrative(administrativeName, userRut)) {
            return ResponseEntity.status(403).body("No tienes permisos para crear la actividad");
        }
        int id = userService.findUserByRut(userRut).get().getId();
        EnvironmentVinculation environmentVinculation = environmentVinculationMapper
                .environmentVinculationDTOToEnvironmentVinculation(environmentVinculationDTO);
        boolean chek = environmentVinculationService.createActivity(environmentVinculation, id);
        if (chek) {
            return ResponseEntity.ok("Actividad creada exitosamente");
>>>>>>> 15fb7ec6ee4be5f5027a92b39ad9db8c592b3906
        } else {
            return ResponseEntity.status(401).body("Usuario sin permisos");
        }
    }

<<<<<<< HEAD
    // metodo para modificar las actividades de vinculacion con el medio
    @PutMapping("/update/{userRut}")
    public ResponseEntity<EnvironmentVinculationDTO> updateEnvironmentVinculation(@PathVariable String userRut, @RequestBody EnvironmentVinculationDTO dto) {
        int id = userService.findUserByRut(userRut).get().getId();
        EnvironmentVinculationDTO updated = environmentVinculationService.updateEnvironmentVinculation(id, dto);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

=======
>>>>>>> 15fb7ec6ee4be5f5027a92b39ad9db8c592b3906
}
