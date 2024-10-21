package com.Polo.service;

import com.Polo.model.Postulation;
import com.Polo.repository.PostulationRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulationService {

    private final PostulationRepository postulationRepository;

    public void createPostulation(Postulation postulation) {
        if (postulation != null) {
            postulationRepository.save(postulation);
        } else {
            System.out.println("Error al crear la postulacion");
        }
    }

    public List<Postulation> findAllPostulations() {
        return postulationRepository.findAll();
    }

    public Optional<Postulation> findPostulationById(int id) {
        return postulationRepository.findById(id);
    }

    public boolean deletePostulation(int id) {
        if (postulationRepository.existsById(id)) {
            postulationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
