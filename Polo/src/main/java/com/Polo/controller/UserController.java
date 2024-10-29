package com.Polo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.Polo.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://127.0.0.1:5500")
public class UserController {

    private final UserService userService;

    // crear usuarios
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        boolean chek = userService.createUser(user);
        if (chek) {
            return ResponseEntity.ok("Usuario creado exitosamente");
        } else {
            return ResponseEntity.status(404).body("Usuario no creado");
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
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String rut, @RequestParam String password, HttpSession session) {
        Optional<User> user = userService.findUserByRut(rut);
        // Validar el login
        if (user.isPresent() && userService.validateLogin(rut, password)) {
            User userSession = user.get();
            // Crear datos de sesión
            session.setAttribute("userRut", rut);  // Guarda el rut en la sesión
            session.setAttribute("username", userSession.getUserName()); // guarda el nombre de la sesion
            session.setAttribute("lastName", userSession.getUserLastName()); // guarda el apellido de la sesion
            session.setAttribute("role", userSession.getUserRole()); // guarda el rol de la sesion

            System.out.println("Sesion iniciada correctamente");
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
    // @PutMapping("/assignRole/{adminName}")
    @PutMapping("/assignRole")
    public ResponseEntity<String> assignRoleByAdmin(/*@PathVariable String adminName, */@RequestParam String userName, @RequestParam String newRole, HttpSession session) {

        String sessionRole = (String) session.getAttribute("role"); // Verificar si el usuario que está intentando asignar es ADMIN
        if (sessionRole == null || !sessionRole.equals("ADMIN")) {
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

    // autenticacion de usuario
    @GetMapping("/usuario")
    public String getUsuario(@AuthenticationPrincipal UserDetails userDetails) {
        return "Usuario autenticado: " + userDetails.getUsername();
    }

    // Método para obtener datos de usuario (verificación de sesión)
    @GetMapping("/session-info")
    public ResponseEntity<String> getSessionInfo(HttpSession session) {
        String userRut = (String) session.getAttribute("userRut");
        String username = (String) session.getAttribute("username");
        String lastName = (String) session.getAttribute("lastName");
        String role = (String) session.getAttribute("role");

        if (userRut != null) {
            return ResponseEntity.ok("Usuario en sesión: " + userRut + ", Nombre: " + username + ", Apellido: " + lastName + ", Rol: " + role);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión iniciada");
        }
    }

    // Método de logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();  // Invalida la sesión
        return ResponseEntity.ok("Logout correcto");
    }

}
