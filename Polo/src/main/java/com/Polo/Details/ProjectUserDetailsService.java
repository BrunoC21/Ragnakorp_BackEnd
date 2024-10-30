package com.Polo.Details;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Polo.model.Project;
import com.Polo.model.User;
import com.Polo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectUserDetailsService {

    private final UserRepository userRepository;

    public void saveDetails(Project project, String userRut) {
    
        Optional<User> user = userRepository.findByUserRut(userRut);
        if (project != null) {
            if (user.get().getProjects() == null) {
                user.get().setProjects(new ArrayList<>());
            }

            if (!user.get().getProjects().contains(project)) {
                user.get().getProjects().add(project);
            }
            userRepository.save(user.get());
        } else {
            System.out.println("Usuario no encontrado con el rut: " + userRut);
        }

    }

}
