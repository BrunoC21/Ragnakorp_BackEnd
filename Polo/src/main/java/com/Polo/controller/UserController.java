package com.Polo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @CrossOrigin("http://127.0.0.1:5500") // una vez se lance la app esto debe eliminarse porque corre en servidor
    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    // eliminar usuarios
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok("Usuario eliminado exitosamente");
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

    // buscar usuario por nombre
    @GetMapping("search/rut/{userRut}")
    public ResponseEntity<User> findUserByRut(@PathVariable String userRut) {
        Optional<User> user = userService.findUserByRut(userRut);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // buscar usuario por nombre
    @GetMapping("search/name/{userName}")
    public ResponseEntity<User> findUserByName(@PathVariable String userName) {
        Optional<User> user = userService.findUserByName(userName);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // apartado login
    @CrossOrigin("http://127.0.0.1:5500") // una vez se lance la app esto debe eliminarse porque corre en servidor
    @PostMapping("login")
    public String loginUser(@RequestParam String rut, @RequestParam String password) {
        // Validar el login
        if (userService.validateLogin(rut, password)) {
            return "Login successful!";
        } else {
            return "Invalid username or password";
        }
    }

    // apartado para eliminar usuarios
    @DeleteMapping("/deleteUser/{adminName}")
    public ResponseEntity<String> deleteUserByAdmin(@PathVariable String adminName, @RequestParam String userName) {
        
        // Validar si el usuario que está solicitando es ADMIN
        if (!userService.isAdmin(adminName)) {
            return new ResponseEntity<>("User Admin isn't ADMIN", HttpStatus.FORBIDDEN);
        }

        if (!userService.isAdmin(userName)) {
            return new ResponseEntity<>("User trying to delete is an ADMIN", HttpStatus.FORBIDDEN);
        }
        
        // Intentar eliminar al usuario
        boolean check = userService.deleteUserByName(userName);
        
        if (check) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User cannot be deleted", HttpStatus.NOT_FOUND);
        }
    }

    // apartado para asignar roles a los usuarios
    @PutMapping("/assignRole/{adminName}")
    public ResponseEntity<String> assignRoleByAdmin(@PathVariable String adminName, @RequestParam String userName, @RequestParam String newRole) {
    
        // Verificar si el usuario que está intentando asignar es ADMIN
        if (!userService.isAdmin(adminName)) {
            return new ResponseEntity<>("User is not an ADMIN", HttpStatus.FORBIDDEN);
        }
        
        // Intentar asignar el nuevo rol al usuario
        boolean isUpdated = userService.updateUserRole(userName, newRole);
        
        if (isUpdated) {
            return new ResponseEntity<>("Role updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found or role invalid", HttpStatus.BAD_REQUEST);
        }
    }
}
