package com.Polo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.Polo.model.User;
import com.Polo.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // crear usuarios
    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    // eliminar usuarios
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok("Usuario eliminado existosamente");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    // buscar todos los usuarios
    @GetMapping("/search")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    // buscar usuario por id
    @GetMapping("/search/{id}")
    public Optional<User> findUserById(@PathVariable int id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            return user;
        } else {
            return null;
        }
    }

}
