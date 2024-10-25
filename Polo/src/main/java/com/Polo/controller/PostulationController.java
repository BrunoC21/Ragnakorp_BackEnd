package com.Polo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.Polo.model.Postulation;
import com.Polo.service.PostulationService;

@RestController
@RequestMapping("/postulation")
@RequiredArgsConstructor

public class PostulationController {
    private final PostulationService postulationService;

    // crear postulaciones
    @PostMapping("/create")
    public void createPostulation(@RequestBody Postulation postulation) {
        postulationService.createPostulation(postulation);
    }

    // eliminar postulaciones
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePostulation(@PathVariable int id) {
        boolean isDeleted = postulationService.deletePostulation(id);
        if (isDeleted) {
            return ResponseEntity.ok("Postulacion eliminada existosamente");
        } else {
            return ResponseEntity.status(404).body("Postulacion no encontrada");
        }
    }

    // buscar todas las postulaciones
    @GetMapping("/search")
    public List<Postulation> findAllPostulations() {
        return postulationService.findAllPostulations();
    }

    // buscar postulacion por id
    @GetMapping("/search/{id}")
    public Optional<Postulation> findPostulationById(@PathVariable int id) {
        Optional<Postulation> postulation = postulationService.findPostulationById(id);
        if (postulation.isPresent()) {
            return postulation;
        } else {
            return null;
        }
    }
}
