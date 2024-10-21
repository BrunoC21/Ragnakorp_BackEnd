package com.Polo.Details;

import java.util.ArrayList;

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
        String postulationName = postulation.getPostulationName();

        User user = userRepository.findByUserName(postulationName);
        if (user != null) {
            if (user.getPostulation() == null) {
                user.setPostulation(new ArrayList<>());
            }

            if (!user.getPostulation().contains(postulation)) {
                user.getPostulation().add(postulation);
            }
            userRepository.save(user);
        } else {
            System.out.println("Usuario no encontrado con el nombre: " + postulationName);
        }

    }

}
