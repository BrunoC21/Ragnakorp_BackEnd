package com.Polo.service;

import com.Polo.model.*;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import com.Polo.repository.EnvironmentVinculationRepository;
import com.Polo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnvironmentVinculationService {
    private final EnvironmentVinculationMapper mapper = Mappers.getMapper(EnvironmentVinculationMapper.class);
    private final EnvironmentVinculationRepository environmentVinculationRepository;
    private final UserRepository userRepository;

    public boolean createActivity(EnvironmentVinculation environmentVinculation, int id) {
        if (environmentVinculation != null) {
            // Verificar si la actividad ya existe por nombre
            Optional<EnvironmentVinculation> existingEnvironmentVinculation = environmentVinculationRepository
                    .findByActivityName(environmentVinculation.getActivityName());
            if (existingEnvironmentVinculation.isPresent()) {
                System.out.println("La actividad ya existe.");
                return false;
            }

            // Buscar el usuario por ID
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                System.out.println("Usuario no encontrado.");
                return false;
            }

            // Asociar el usuario a la actividad
            environmentVinculation.setUser(userOptional.get());

            // Guardar la actividad
            environmentVinculationRepository.save(environmentVinculation);
            System.out.println("Actividad creada exitosamente.");
            return true;
        }

        System.out.println("El objeto environmentVinculation es nulo.");
        return false;
    }

    public List<EnvironmentVinculationDTO> findAllActivities() {
        List<EnvironmentVinculation> environmentVinculationList = environmentVinculationRepository.findAll();
        List<EnvironmentVinculationDTO> environmentVinculationDTOList;
        environmentVinculationDTOList = mapper
                .environmentVinculationListToEnvironmentVinculationDTOList(environmentVinculationList);
        System.out.println("todas las actividades encontradas");
        return environmentVinculationDTOList;
    }

    public Optional<EnvironmentVinculationDTO> findByActivityId(int id) {
        Optional<EnvironmentVinculation> optional = environmentVinculationRepository.findById(id);
        if (optional.isPresent()) {
            System.out.println("Actividad encontrada");
            return Optional.of(mapper.environmentVinculationToEnvironmentVinculationDTO(optional.get()));
        }
        System.out.println("Actividad no encontrada");
        return Optional.empty();
    }

    public boolean deleteActivity(int id) {
        Optional<EnvironmentVinculation> optional = environmentVinculationRepository.findById(id);
        if (optional.isPresent()) {
            environmentVinculationRepository.deleteById(id);
            System.out.println("Actividad eliminada");
            return true;
        }
        System.out.println("Actividad no encontrada");
        return false;
    }

    public Optional<EnvironmentVinculationDTO> findByActivityName(String activityName) {
        Optional<EnvironmentVinculation> optional = environmentVinculationRepository.findByActivityName(activityName);
        if (optional.isPresent()) {
            System.out.println("Actividad encontrada");
            return Optional.of(mapper.environmentVinculationToEnvironmentVinculationDTO(optional.get()));
        }
        System.out.println("Actividad no encontrada");
        return Optional.empty();
    }
}