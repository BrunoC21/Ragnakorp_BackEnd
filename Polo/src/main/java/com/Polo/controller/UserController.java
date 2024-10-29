package com.Polo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.Polo.model.User;
import com.Polo.model.UserDTO;
import com.Polo.model.UserMapper;
import com.Polo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://127.0.0.1:5500")
public class UserController {

    // private final UserService userService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    // crear usuarios
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        // Convertir UserDTO a User
        User user = userMapper.userDTOToUser(userDTO);

        // Llamar al servicio para crear el usuario
        boolean check = userService.createUser(user);
        if (check) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no creado");
        }
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
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserDTO> userDTOList = userService.findAllUsers();
        if (!userDTOList.isEmpty()) {
            return new ResponseEntity<>(userDTOList, HttpStatus.OK);
        } else { 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar usuario por id
    @GetMapping("/search/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable int id) {
        Optional<UserDTO> userDTO = userService.findUserById(id);
        if (userDTO.isPresent()) {
            return new ResponseEntity<>(userDTO.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar usuario por nombre
    @GetMapping("search/rut/{userRut}")
    public ResponseEntity<UserDTO> findUserByRut(@PathVariable String userRut) {
        Optional<UserDTO> userDTO = userService.findUserByRut(userRut);
        if (userDTO.isPresent()) {
            return new ResponseEntity<>(userDTO.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar usuario por nombre
    @GetMapping("search/name/{userName}")
    public ResponseEntity<UserDTO> findUserByName(@PathVariable String userName) {
        Optional<UserDTO> userDTO = userService.findUserByName(userName);
        if (userDTO.isPresent()) {
            return new ResponseEntity<>(userDTO.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // apartado login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String rut, @RequestParam String password) {
        Optional<UserDTO> userDTO = userService.findUserByRut(rut);
        if (userDTO.isPresent() && userService.validateLogin(rut, password)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login correcto");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // apartado para eliminar usuarios
    @DeleteMapping("/deleteUser/{adminName}")
    public ResponseEntity<String> deleteUserByAdmin(@PathVariable String adminName, @RequestParam String userName) {

        // Validar si el usuario que está solicitando es ADMIN
        if (!userService.isAdmin(adminName)) {
            return new ResponseEntity<>("User Admin isn't ADMIN", HttpStatus.FORBIDDEN);
        } else {
            System.out.println("El admin name esta correcto");
        }

        if (userService.isAdmin(userName)) {
            return new ResponseEntity<>("User trying to delete is an ADMIN", HttpStatus.FORBIDDEN);
        } else {
            System.out.println("El user name esta correcto");
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

        if (!userService.isAdmin(adminName)) {
            return new ResponseEntity<>("User Admin isn't ADMIN", HttpStatus.FORBIDDEN);
        } else {
            System.out.println("El admin name esta correcto");
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
