package com.Polo.ServiceDTO;

import com.Polo.DTO.PostulationDTO;
import com.Polo.DTO.UserPostulationDTO;
import com.Polo.model.Postulation;
import com.Polo.model.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPostulationService {

    // Método que convierte User en UserPostulationDTO
    public UserPostulationDTO convertToUserPostulationDTO(User user) {
        UserPostulationDTO dto = new UserPostulationDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());

        // Convertir las postulaciones de la entidad User en DTOs
        List<PostulationDTO> postulationDTOs = user.getPostulation().stream()
            .map(this::convertToPostulationDTO)
            .collect(Collectors.toList());
        
        dto.setPostulations(postulationDTOs);
        return dto;
    }

    // Método que convierte Postulation en PostulationDTO
    private PostulationDTO convertToPostulationDTO(Postulation postulation) {
        PostulationDTO dto = new PostulationDTO();
        dto.setId(postulation.getId());
        dto.setPostulationName(postulation.getPostulationName());
        dto.setPostulationDescription(postulation.getPostulationDescription());
        return dto;
    }
}