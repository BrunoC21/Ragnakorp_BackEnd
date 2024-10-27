package com.Polo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Polo.model.User;
import com.Polo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean createUser(User user) {
        if (user != null) {
            // Validar si el usuario ya existe por el email o username
            User existingUser = userRepository.findByUserRut(user.getUserRut());
            if (existingUser != null) {
                System.out.println("El usuario ya existe.");
                return false;
            } else {
                // Si no existe, guardarlo
                userRepository.save(user);
                System.out.println("Usuario creado exitosamente.");
                return true;
            }
        } else {
            System.out.println("Error al crear el usuario");
            return false;
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
    public Optional<User> findUserByName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    public boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Método para eliminar al usuario
    public boolean deleteUserByName(String userName) {
        User user = userRepository.findByUserName(userName);

        // Si el usuario existe, lo eliminamos por ID
        if (user != null) {
            if (!isAdmin(user.getUserName())) {
                return false;
            }
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    // login user
    public boolean validateLogin(String rut, String password) {
        User user = userRepository.findByUserRut(rut);
        return user != null && user.getUserPassword().equals(password);
    }

    // Método para verificar si el usuario es ADMIN
    public boolean isAdmin(String userName) {
        User user = userRepository.findByUserName(userName);

        // Verificar si el usuario tiene el rol ADMIN
        return user != null && "ADMIN".equals(user.getUserRole());
    }

    // Metodo para verificar si el usuario es Adminsitrativo
    public boolean isAdministrative(String userName, String userRut) {
        Optional<User> user = userRepository.findByUserNameAndUserRut(userName, userRut);
        System.out.println(user.get().getUserRole());
        // Verificar si el usuario encontrado tiene el rol ADMINISTRATIVE
        return user.isPresent() && "ADMINISTRATIVE".equals(user.get().getUserRole());
    }

    // Método para actualizar el rol del usuario
    public boolean updateUserRole(String userName, String newRole) {
        User user = userRepository.findByUserName(userName);

        // Si el usuario existe, actualizar su rol
        if (user != null) {
            try {
                Role role = Role.valueOf(newRole.toUpperCase());
                user.setUserRole(role.name());
                userRepository.save(user); // Guardar los cambios en la base de datos
                return true;
            } catch (IllegalArgumentException e) {
                // Si el rol no es válido, capturamos la excepción
                return false;
            }
        }
        return false;
    }
}
