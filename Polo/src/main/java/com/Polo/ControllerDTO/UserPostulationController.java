package com.Polo.ControllerDTO;

import com.Polo.DTO.UserPostulationDTO;
import com.Polo.model.User;
import com.Polo.ServiceDTO.UserPostulationService;
import com.Polo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPostulationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPostulationService userPostulationService;

    // Obtener postulaciones de un usuario por su ID
    @GetMapping("/user/{id}/postulations")
    public ResponseEntity<UserPostulationDTO> getUserPostulations(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Convertir el User en UserPostulationDTO
        UserPostulationDTO dto = userPostulationService.convertToUserPostulationDTO(user);

        return ResponseEntity.ok(dto);
    }
}