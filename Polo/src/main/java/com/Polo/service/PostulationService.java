package com.Polo.service;

import com.Polo.Details.PostulationProjectDetailsService;
import com.Polo.Details.PostulationUserDetailsService;
import com.Polo.model.*;
import com.Polo.repository.PostulationRepository;
import com.Polo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulationService {

    private final PostulationRepository postulationRepository;
    private final PostulationUserDetailsService postulationUserDetailsService;
    private final PostulationProjectDetailsService postulationProjectDetailsService;
    private final UserRepository userRepository;

    private final PostulationMapper mapper = Mappers.getMapper(PostulationMapper.class);

    public boolean createPostulation(Postulation postulation) {
        if (postulation != null) {
            postulationRepository.save(postulation);
            postulationUserDetailsService.saveDetails(postulation);
            postulationProjectDetailsService.saveDetails(postulation);
            System.out.println("Postulacion creada exitosamente");
            return true;
        } else {
            System.out.println("Error al crear la postulacion");
            return false;
        }
    }

    public List<PostulationDTO> findAllPostulations() {
        List<Postulation> postulationList = postulationRepository.findAll();
        List<PostulationDTO> postulationDTOList;
        postulationDTOList = mapper.postulationListToPostulationDTOList(postulationList);
        return postulationDTOList;
    }

    public Optional<PostulationDTO> findPostulationById(int id) {
        Optional<Postulation> optional = postulationRepository.findById(id);
        if (optional.isPresent()) {
            System.out.println("Postulacion encontrada: ");
            return Optional.of(mapper.postulationToPostulationDTO(optional.get()));
        }
        System.out.println("Postulacion no encontrada");
        return Optional.empty();
    }

    public boolean deletePostulation(int id) {
        if (postulationRepository.existsById(id)) {
            postulationRepository.deleteById(id);
            System.out.println("Postulacion eliminada exitosamente");
            return true;
        }
        System.out.println("Error al eliminar la postulacion");
        return false;
    }

    public boolean updatePostulationStatus(Integer id, PostulationDTO postulationDTO) {
        Optional<Postulation> optionalEntity = postulationRepository.findById(id);

        if (optionalEntity.isPresent()) {
            Postulation entity = optionalEntity.get();

            // Actualizar los campos con los datos del DTO
            entity.setPostulationName(postulationDTO.getPostulationName());
            entity.setPostulationRut(postulationDTO.getPostulationRut());
            entity.setPostulationDescription(postulationDTO.getPostulationDescription());
            entity.setPostulationProject(postulationDTO.getPostulationProject());
            entity.setPostulationStatus(postulationDTO.getPostulationStatus());

            // Guardar los cambios
            Postulation updatedEntity = postulationRepository.save(entity);

            if (updatedEntity != null) {
                System.out.println("postulacion actualizada");
            }

            // Retornar la entidad actualizada como DTO
            return true;
        }

        return false; // No se encontró la entidad
    }

    
    public List<PostulationDTO> getPostulationsByUser(String userRut) {
        // Recuperar el usuario desde la base de datos usando el RUT
        User user = userRepository.findByUserRut(userRut).orElseThrow(() -> new RuntimeException("Usuario no encontrado con RUT: " + userRut));

        // Convertir las postulaciones del usuario a DTO
        return user.getPostulation().stream()
                .map(postulation -> {
                    PostulationDTO dto = new PostulationDTO();
                    dto.setId(postulation.getId());
                    dto.setPostulationName(postulation.getPostulationName());
                    dto.setPostulationRut(postulation.getPostulationRut());
                    dto.setPostulationDescription(postulation.getPostulationDescription());
                    dto.setPostulationProject(postulation.getPostulationProject());
                    dto.setPostulationStatus(postulation.getPostulationStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
}
