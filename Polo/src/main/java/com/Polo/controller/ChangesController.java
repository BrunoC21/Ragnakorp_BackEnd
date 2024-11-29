package com.Polo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.model.Changes;
import com.Polo.model.ChangesDTO;
import com.Polo.model.ChangesMapper;
import com.Polo.model.NewsDTO;
import com.Polo.model.ProjectDTO;
import com.Polo.model.UserDTO;
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
        ProjectDTO projectDTO = objectMapper.convertValue(payload.get("projectData"), ProjectDTO.class);

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

