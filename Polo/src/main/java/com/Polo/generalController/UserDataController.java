package com.Polo.generalController;

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
    public ResponseEntity<String> getSessionInfo(HttpSession session) {
        String userRut = (String) session.getAttribute("userRut");
        String username = (String) session.getAttribute("username");
        String lastName = (String) session.getAttribute("lastName");
        String role = (String) session.getAttribute("role");

        if (userRut != null) {
            // Devuelve los datos en el formato deseado
            return ResponseEntity.ok("Usuario en sesión: " + userRut + ", Nombre: " + username + ", Apellido: " + lastName + ", Rol: " + role);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión iniciada");
        }
    }
}
