package com.Polo.generalController;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/userData")
@CrossOrigin("http://127.0.0.1:5500") // Asegúrate de configurar el CORS para tu frontend
public class UserDataController {

    // Método para obtener datos de usuario desde la sesión
    @GetMapping("/session-info")
    public ResponseEntity<Object> getSessionInfo(HttpSession session) {
        String userRut = (String) session.getAttribute("userRut");
        String username = (String) session.getAttribute("username");
        String lastName = (String) session.getAttribute("lastName");
        String role = (String) session.getAttribute("role");

        System.out.println(userRut + " " + username + " " + userRut);

        if (userRut != null) {
            return ResponseEntity.ok().body(Map.of(
                "userRut", userRut,
                "username", username,
                "lastName", lastName,
                "role", role
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión iniciada");
        }
    }

    
}
