package com.Polo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.Polo.userDataSession.SessionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class UserController {

    // private final UserService userService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    // crear usuarios
    @PostMapping("/create")
    public ResponseEntity<String> createUser(/*@RequestBody UserDTO userDTO*/@RequestBody Map<String, Object> session) {

        // Crear una instancia de ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        @SuppressWarnings("unchecked")
        Map<String, Object> sessionData = (Map<String, Object>) session.get("sessionData");
        UserDTO userDTO = objectMapper.convertValue(session.get("userDTO"), UserDTO.class);

        // Validar sesión
        String role = sessionData.get("role").toString();

        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no tiene el rol necesario");
        }

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
            return new ResponseEntity<>(userDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar usuario por nombre
    @GetMapping("/search/rut/{userRut}")
    public ResponseEntity<UserDTO> findUserByRut(@PathVariable String userRut) {
        Optional<UserDTO> userDTO = userService.findUserByRut(userRut);
        if (userDTO.isPresent()) {
            return new ResponseEntity<>(userDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // buscar usuario por nombre REVISAR
    @GetMapping("/search/name/{userName}")
    public ResponseEntity<UserDTO> findUserByName(@PathVariable String userName) {
        Optional<UserDTO> userDTO = userService.findUserByName(userName);
        if (userDTO.isPresent()) {
            return new ResponseEntity<>(userDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // apartado login
    @PostMapping("/login")
    @CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String rut, @RequestParam String password, HttpSession session) {
        System.out.println(rut);
        Optional<UserDTO> userDTO = userService.findUserByRut(rut);
        if (userDTO.isPresent() && userService.validateLogin(rut, password)) {
            // Establecer los datos de sesión
            SessionUtils.setUserSession(userDTO.get(), rut, session);

            // Crear el mapa con los datos de sesión
            Map<String, Object> sessionData = new HashMap<>();
            sessionData.put("userRut", rut);
            sessionData.put("username", userDTO.get().getUserName());
            sessionData.put("lastName", userDTO.get().getUserLastName());
            sessionData.put("role", userDTO.get().getUserRole());
            sessionData.put("email", userDTO.get().getUserEmail());
            sessionData.put("phone", userDTO.get().getUserPhone());

            System.out.println(" ");
            System.out.println(sessionData);
            System.out.println(" ");

            // Retornar los datos de sesión y una respuesta HTTP 200 OK
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .header("Set-Cookie", "JSESSIONID=" + session.getId() + "; Path=/; HttpOnly; SameSite=None; Secure")
                    .body(sessionData);
        } else {
            // Si el login falla, retornar un error 401 con un mensaje
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        }
    }

    // apartado para asignar rol actualizado a datos de sesion
    @PutMapping("/assignRole")
    public ResponseEntity<String> assignRoleByAdmin(@RequestParam String userRut, @RequestParam String newRole, @RequestBody Map<String, Object> session) {

        // extraer datos de sesion
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionData = (Map<String, Object>) session.get("sessionData");

        String role = sessionData.get("role").toString();

        if ("ADMIN".equals(role)) {
            System.out.println("INGRESASTE");
            // Intentar asignar el nuevo rol al usuario
            boolean isUpdated = userService.updateUserRole(userRut, newRole);

            if (isUpdated) {
                return new ResponseEntity<>("Role updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found or role invalid", HttpStatus.BAD_REQUEST);
            }
        } else {
            System.out.println("NO TIENE ROL DE ADMIN");
            return new ResponseEntity<>("User Admin isn't ADMIN", HttpStatus.FORBIDDEN);
        }
    }

    // apartado para recuperar los datos de sesion
    @GetMapping("/sessionInfo")
    @CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
    public ResponseEntity<Map<String, Object>> getSessionInfo(HttpSession session) {
        Map<String, Object> sessionData = SessionUtils.getUserSession(session);
        if (sessionData.isEmpty() || sessionData.get("userRut") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "No hay sesión iniciada"));
        }
        return ResponseEntity.ok(sessionData);
    }

}
