package com.Polo.service;

import com.Polo.Details.PostulationProjectDetailsService;
import com.Polo.Details.PostulationUserDetailsService;
import com.Polo.model.*;
import com.Polo.repository.PostulationRepository;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulationService {

    private final PostulationRepository postulationRepository;
    private final PostulationUserDetailsService postulationUserDetailsService;
    private final PostulationProjectDetailsService postulationProjectDetailsService;

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
            return Optional.of(mapper.postulationToPostulationDTO(optional.get()));
        }
        return Optional.empty();
    }

    public boolean deletePostulation(int id) {
        if (postulationRepository.existsById(id)) {
            postulationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
