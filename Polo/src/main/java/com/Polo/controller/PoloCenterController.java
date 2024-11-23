package com.Polo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.model.PoloCenterDTO;
import com.Polo.model.PoloCenterMapper;
import com.Polo.model.Polocenter;
import com.Polo.service.PoloCenterService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/polocenter")
@RequiredArgsConstructor
public class PoloCenterController {
    private final PoloCenterService poloCenterService;
    private final PoloCenterMapper poloCenterMapper;

    // @PostMapping("/create")
    // public ResponseEntity<String> createPoloCenter(@RequestBody Polocenter poloCenter) {
    //     boolean chek = poloCenterService.createPoloCenter(poloCenter);
    //     if (chek) {
    //         return ResponseEntity.ok("Centro creado exitosamente");
    //     } else {
    //         return ResponseEntity.status(404).body("Centro no creado");
    //     }
    // }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePoloCenter(@PathVariable int id) {
        boolean isDeleted = poloCenterService.deletePoloCenter(id);
        if (isDeleted) {
            return ResponseEntity.ok("Centro eliminado exitosamente");
        } else {
            return ResponseEntity.status(404).body("Centro no encontrado");
        }
    }

    @GetMapping("/search")
    public List<Polocenter> findAllPoloCenters() {
        return poloCenterService.findAllPoloCenters();
    }

    @GetMapping("/search/{centerName}")
    public ResponseEntity<Polocenter> findPoloCenterByCenterName(@PathVariable String centerName) {
        Optional<Polocenter> poloCenter = poloCenterService.findPoloCenterByCenterName(centerName);
        return poloCenter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPoloCenterByAdministrativeName(@RequestBody Map<String, Object> session) {

        // Crear una instancia de ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // extraer datos de sesion
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionData = (Map<String, Object>) session.get("sessionData");
        PoloCenterDTO poloC = objectMapper.convertValue(session.get("poloCenter"), PoloCenterDTO.class);

        Polocenter polocenter = poloCenterMapper.toEntity(poloC);

        String role = sessionData.get("role").toString();

        if ("ADMINISTRATIVE".equals(role)) {
            boolean chek = poloCenterService.createPoloCenter(polocenter);
            if (chek) {
                return ResponseEntity.ok("Centro creado exitosamente");
            } else {
                return ResponseEntity.status(400).body("Centro no creado");
            }
        } else {
            return ResponseEntity.status(403).body("Usuario sin permisos");
        }
    }

}
