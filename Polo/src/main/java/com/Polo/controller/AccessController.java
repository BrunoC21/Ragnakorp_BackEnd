package com.Polo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Polo.model.AccessDTO;
import com.Polo.service.AccessService;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/access") // Base path
public class AccessController {

    @Autowired
    private AccessService accessService;

    @PostMapping("/create")
    public AccessDTO registerAccess(@RequestBody AccessDTO accessDTO, HttpServletRequest request) {
        // Obtener el User-Agent desde el encabezado de la solicitud
        String userAgent = request.getHeader("User-Agent");
        accessDTO.setDevice(userAgent); // Configurar el dispositivo en el DTO
        return accessService.registerAccess(accessDTO);
    }

    @GetMapping("/search")
    public List<AccessDTO> getAllAccess() {
        return accessService.getAllAccess();
    }
}
