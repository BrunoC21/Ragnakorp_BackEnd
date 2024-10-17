package com.Polo.service;

import com.Polo.model.User;
import com.Polo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user) {
        if (user != null) {
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

    // public User updateUser(Integer id, User userDetails) {
    //     User user = userRepository.findById(id).orElseThrow();
    //     user.setUserName(userDetails.getUserName());
    //     user.setUserLastname(userDetails.getUserLastname());
    //     user.setUserPhone(userDetails.getUserPhone());
    //     user.setUserEmail(userDetails.getUserEmail());
    //     return userRepository.save(user);
    // }

    public boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
