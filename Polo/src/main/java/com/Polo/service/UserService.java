package com.Polo.service;

import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.Polo.model.*;
import com.Polo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public boolean createUser(User user) {
        if (user != null) {
            // Validar si el usuario ya existe por el userRut
            Optional<User> existingUser = userRepository.findByUserRut(user.getUserRut());
            if (existingUser == null) {
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

    public List<UserDTO> findAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList;
        userDTOList = mapper.userListToUserDTOList(userList);
        return userDTOList;
    }

    public Optional<UserDTO> findUserById(int id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return Optional.of(mapper.userToUserDTO(optional.get()));
        }
        return Optional.empty();
    }

    // busqueda de usuario por rut
    public Optional<UserDTO> findUserByRut(String userRut) {
        Optional<User> optional = userRepository.findByUserRut(userRut);
        if (optional.isPresent()) {
            return Optional.of(mapper.userToUserDTO(optional.get()));
        }
        return Optional.empty();
    }

    // busqueda de usuario por nombre
    public Optional<UserDTO> findUserByName(String userName) {
        Optional<User> optional = userRepository.findByUserName(userName);
        if (optional.isPresent()) {
            return Optional.of(mapper.userToUserDTO(optional.get()));
        }
        return Optional.empty();
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
        Optional<User> optional = userRepository.findByUserName(userName);

        // Si el usuario existe, lo eliminamos
        if (optional != null) {
            if (isAdmin(optional.get().getUserName())) {
                return false;
            }
            userRepository.delete(optional.get());
            return true;
        }
        return false;
    }

    // login user
    public boolean validateLogin(String rut, String password) {
        Optional<User> optional = userRepository.findByUserRut(rut);
        return optional != null && optional.get().getUserPassword().equals(password);
    }

    // Método para verificar si el usuario es ADMIN
    public boolean isAdmin(String userName) {
        Optional<User> optional = userRepository.findByUserName(userName);

        // Verificar si el usuario tiene el rol ADMIN
        return optional != null && "ADMIN".equals(optional.get().getUserRole());
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
        Optional<User> optional = userRepository.findByUserName(userName);
        
        // Si el usuario existe, actualizar su rol
        if (optional != null) {
            System.out.println("USUARIO ENCONTRADO");
            try {
                System.out.println("ROL ACTUALIZADO");
                Role role = Role.valueOf(newRole.toUpperCase());
                optional.get().setUserRole(role.name());
                userRepository.save(optional.get()); // Guardar los cambios en la base de datos
                return true;
            } catch (IllegalArgumentException e) {
                // Si el rol no es válido, capturamos la excepción
                return false;
            }
        }
        return false;
    }
}
