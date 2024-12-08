package com.Polo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.model.Changes;
import com.Polo.model.ChangesDTO;
import com.Polo.model.ChangesMapper;
import com.Polo.model.NewsDTO;
import com.Polo.model.ProjectDTO;
import com.Polo.service.ChangesService;
import com.Polo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/changes")
@RequiredArgsConstructor
@CrossOrigin("http://127.0.0.1:5500")
public class ChangesController {

    private final UserService userService;
    private final ChangesMapper changesMapper;
    private final ChangesService changesService; // Asumiendo que `ChangesService` también se inyecta

    @PostMapping("/create")
    public void createChange(@RequestBody Map<String, Object> payload, String tipo) {
        if ("noticia".equals(tipo)) {
            handleNewsChange(payload);
        } else {
            handleProjectChange(payload);
        }
    }

    @GetMapping("/search/News")
    public ResponseEntity<List<ChangesDTO>> findAllNewsChanges() {
        System.out.println("INGRESASTE AL METODO");
        List<ChangesDTO> newsChangesDTO = changesService.findAllNewsChanges();
        if (!newsChangesDTO.isEmpty()) {
            return new ResponseEntity<>(newsChangesDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/Project")
    public ResponseEntity<List<ChangesDTO>> findAllProjectChanges() {
        System.out.println("INGRESASTE AL METODO");
        List<ChangesDTO> projectsChangesDTO = changesService.findAllProjectChanges();
        if (!projectsChangesDTO.isEmpty()) {
            return new ResponseEntity<>(projectsChangesDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void handleNewsChange(Map<String, Object> payload) {
        ObjectMapper objectMapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
        Map<String, Object> sessionData = (Map<String, Object>) payload.get("sessionData");
        NewsDTO newsDTO = objectMapper.convertValue(payload.get("news"), NewsDTO.class);

        String rut = sessionData.get("userRut").toString();
        String idNoticia = newsDTO.getId().toString();

        ChangesDTO changesDTO = new ChangesDTO();
        changesDTO.setChangesDescription("Noticia");
        changesDTO.setChangesThing(idNoticia);
        changesDTO.setChangesType("Update");
        changesDTO.setChangeIdUser(userService.findUserByRut2(rut));

        Changes changes = changesMapper.changesDTOToChanges(changesDTO);
        boolean check = changesService.createChange(changes);

        System.out.println(check ? "Cambio guardado" : "Cambio no guardado");
    }

    private void handleProjectChange(Map<String, Object> payload) {
        ObjectMapper objectMapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
        Map<String, Object> sessionData = (Map<String, Object>) payload.get("sessionData");
        ProjectDTO projectDTO = objectMapper.convertValue(payload.get("project"), ProjectDTO.class);

        String rut = sessionData.get("userRut").toString();
        String idProyecto = projectDTO.getId().toString();

        ChangesDTO changesDTO = new ChangesDTO();
        changesDTO.setChangesDescription("Proyecto");
        changesDTO.setChangesThing(idProyecto);
        changesDTO.setChangesType("Update");
        changesDTO.setChangeIdUser(userService.findUserByRut2(rut));

        Changes changes = changesMapper.changesDTOToChanges(changesDTO);
        boolean check = changesService.createChange(changes);

        System.out.println(check ? "Cambio guardado" : "Cambio no guardado");
    }
}

