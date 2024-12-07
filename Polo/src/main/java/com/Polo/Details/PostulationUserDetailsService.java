package com.Polo.Details;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Polo.model.Postulation;
import com.Polo.model.User;
import com.Polo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulationUserDetailsService {

    private final UserRepository userRepository;

    public void saveDetails(Postulation postulation) {
        String postulationRut = postulation.getPostulationRut();

        Optional<User> user = userRepository.findByUserRut(postulationRut);
        if (user != null) {
            if (user.get().getPostulation() == null) {
                user.get().setPostulation(new ArrayList<>());
                userRepository.save(user.get());
            }

            if (!user.get().getPostulation().contains(postulation)) {
                user.get().getPostulation().add(postulation);
            }
        } else {
            System.out.println("Usuario no encontrado con el nombre: " + postulationRut);
        }

    }

}
