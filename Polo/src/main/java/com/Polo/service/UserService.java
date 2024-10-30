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
    public boolean deleteUserByRut(String userRut) {
        Optional<User> optional = userRepository.findByUserRut(userRut);
    
        if (optional.isPresent()) {
            if (isAdmin(optional.get().getUserName())) {
                System.out.println("No se puede eliminar al usuario ADMIN: " + optional.get().getUserName());
                return false; // No se puede eliminar a un admin
            }
            System.out.println("Usuario eliminado: " + optional.get().getUserName());
            userRepository.delete(optional.get());
            return true;
        } else {
            System.out.println("Usuario no encontrado con RUT: " + userRut); // Mensaje adicional
        }
        System.out.println("---------------");
        System.out.println("NO ENCONTRADO");
        System.out.println("---------------");
        return false; // El usuario no existía
    }
    

    // login user
    public boolean validateLogin(String rut, String password) {
        Optional<User> optional = userRepository.findByUserRut(rut);
        return optional != null && optional.get().getUserPassword().equals(password);
    }

    // Método para verificar si el usuario es ADMIN
    public boolean isAdmin(String userName) {
        Optional<User> optional = userRepository.findByUserName(userName);
    
        if (optional.isPresent()) {
            System.out.println("Usuario encontrado: " + optional.get().getUserName() + ", Rol: " + optional.get().getUserRole());
            return "ADMIN".equals(optional.get().getUserRole());
        } else {
            System.out.println("Usuario no encontrado: " + userName);
            return false;
        }
    }

    public boolean isAdminByRut(String userRut) {
        Optional<User> user = userRepository.findByUserRut(userRut);
        // Verifica si el usuario está presente
        if (user.isPresent()) {
            System.out.println(user.get().getUserRole()); // Ahora está seguro de que hay un valor
            return "ADMIN".equals(user.get().getUserRole());
        }
        // Retorna false si el usuario no existe
        return false;
    }
    
    

    // Metodo para verificar si el usuario es Adminsitrativo
    public boolean isAdministrative(String userName, String userRut) {
        Optional<User> user = userRepository.findByUserRut(userRut);
        // Verifica si el usuario está presente
        if (user.isPresent()) {
            System.out.println(user.get().getUserRole()); // Ahora está seguro de que hay un valor
            return "ADMINISTRATIVE".equals(user.get().getUserRole());
        }
        // Retorna false si el usuario no existe
        return false;
    }


    // Metodo para verificar si el usuario es Adminsitrativo
    public boolean isAdministrativeRut(String userRut) {
        Optional<User> user = userRepository.findByUserRut(userRut);
        // Verifica si el usuario está presente
        if (user.isPresent()) {
            System.out.println(user.get().getUserRole()); // Ahora está seguro de que hay un valor
            return "ADMINISTRATIVE".equals(user.get().getUserRole());
        }
        // Retorna false si el usuario no existe
        return false;
    }
    
    

    // Método para actualizar el rol del usuario
    public boolean updateUserRole(String userRut, String newRole) {
        Optional<User> optional = userRepository.findByUserRut(userRut);

        System.out.println(optional.get().getUserName());
        
        // Si el usuario existe, actualizar su rol
        if (optional.isPresent()) {
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
