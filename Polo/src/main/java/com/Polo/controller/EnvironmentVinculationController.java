package com.Polo.controller;

import java.util.List;
import java.util.Optional;

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

import lombok.RequiredArgsConstructor;

import com.Polo.model.*;
import com.Polo.service.EnvironmentVinculationService;
import com.Polo.service.UserService;

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
        } else {
            return ResponseEntity.status(400).body("Actividad no creada");
        }
    }

}
