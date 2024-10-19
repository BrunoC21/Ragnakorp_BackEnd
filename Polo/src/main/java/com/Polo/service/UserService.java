package com.Polo.service;

import com.Polo.model.User;
import com.Polo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        if (user != null) {
            // user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            userRepository.save(user);
        } else {
            System.out.println("Error al crear el usuario");
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    // busqueda de usuario por rut
    public Optional<User> findUserByRut(String userRut) {
        return Optional.ofNullable(userRepository.findByUserRut(userRut));
    }

    // busqueda de usuario por nombre
    public Optional<User>findUserByName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    public boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
