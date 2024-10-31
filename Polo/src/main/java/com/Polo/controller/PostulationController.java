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

import com.Polo.model.Postulation;
import com.Polo.model.PostulationDTO;
import com.Polo.model.PostulationMapper;
import com.Polo.service.PostulationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/postulation")
@RequiredArgsConstructor
@CrossOrigin("http://127.0.0.1:5500")
public class PostulationController {

    private final PostulationService postulationService;
    private final PostulationMapper postulationMapper;

    // crear postulaciones
    @PostMapping("/create")
    public ResponseEntity<String> createPostulation(@RequestBody PostulationDTO postulationDTO) {
        Postulation postulation = postulationMapper.postulationDTOToPostulation(postulationDTO);

        boolean check = postulationService.createPostulation(postulation);
        if (check) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Postulacion creada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Postulacion no creada");
        }
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
    public ResponseEntity<List<PostulationDTO>> findAllPostulations() {
        List<PostulationDTO> postulationDTOList = postulationService.findAllPostulations();
        if (!postulationDTOList.isEmpty()) {
            return new ResponseEntity<>(postulationDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar postulacion por id
    @GetMapping("/search/{id}")
    public ResponseEntity<PostulationDTO> findPostulationById(@PathVariable int id) {
        Optional<PostulationDTO> postulationDTO = postulationService.findPostulationById(id);
        if (postulationDTO.isPresent()) {
            return new ResponseEntity<>(postulationDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
