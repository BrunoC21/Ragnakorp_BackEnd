package com.Polo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Polo.model.Suscription;
import com.Polo.service.SuscriptionService;
import com.Polo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/suscriptor")
@RequiredArgsConstructor
public class SuscriptionController {

    private final SuscriptionService suscriptionService;
    private final UserService userService;

    // crear suscriptor
    @PostMapping("/create")
    public ResponseEntity<String> createSuscription(@RequestBody Suscription suscription) {
        boolean chek = suscriptionService.createSuscription(suscription);

        if (chek) {
            return ResponseEntity.ok("Suscriptor creado exitosamente");
        } else {
            return ResponseEntity.status(404).body("Ya se encuentra suscrito");
        }
    }

    // buscar suscriptor por email
    @GetMapping("/search/{email}")
    public Optional<Suscription> findSuscriptionsByEmail(@PathVariable String email) {
        Optional<Suscription> suscription = suscriptionService.findSuscriptionsByEmail(email);
        if (suscription.isPresent()) {
            return suscription;
        } else {
            return null;
        }
    }

    // autoeliminacion de suscriptor
    @DeleteMapping("/deleteSuscriptor")
    public ResponseEntity<String> deleteSuscriptor(@RequestParam String subEmail) {
        // Intentar eliminar al usuario
        boolean check = suscriptionService.deleteSuscriptorByMail(subEmail);

        if (check) {
            return new ResponseEntity<>("Suscriptior deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Suscriptor cannot be deleted", HttpStatus.NOT_FOUND);
        }
    }

    // elimiar suscriptor por admin
    @DeleteMapping("/deleteSuscriptor/{adminName}")
    public ResponseEntity<String> deleteSuscriptorByAdmin(@PathVariable String adminName, @RequestParam String subEmail) {

        // Validar si el usuario que está solicitando es ADMIN
        if (!userService.isAdmin(adminName)) {
            return new ResponseEntity<>("User Admin isn't ADMIN", HttpStatus.FORBIDDEN);
        } else {
            System.out.println("El admin name esta correcto");
        }

        // Intentar eliminar al usuario
        boolean check = suscriptionService.deleteSuscriptorByMail(subEmail);

        if (check) {
            return new ResponseEntity<>("Suscriptior deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Suscriptor cannot be deleted", HttpStatus.NOT_FOUND);
        }
    }

}
