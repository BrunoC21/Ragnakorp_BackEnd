package com.Polo.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Polo.model.User;
import com.Polo.model.UserMapper;
import com.Polo.repository.UserRepository;
import org.mapstruct.factory.Mappers;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserService userService;

    // Test para verificar la creación de usuario
    @Test
    void siUsuarioNoExisteDebeCrearUsuario() {
        // Arrange
        User user = new User();
        user.setUserRut("98765432-1");
        user.setUserLastName("nuñez");
        user.setUserName("felipe");
        user.setUserEmail("felipe.nuñez@ejemplo.com");
        user.setUserPhone("111111111");
        user.setUserPassword("123");
        user.setUserRole("ADMINISTRATIVE");
        when(userRepository.findByUserRut("98765432-1")).thenReturn(Optional.empty());

        // Act
        boolean resultado = userService.createUser(user);

        // Assert
        assertTrue(resultado); // El usuario debe ser creado
        verify(userRepository, times(1)).save(user); // Verifica que se llame a save
    }

    // Test para verificar que no se crea un usuario ya existente
    @Test
    void siUsuarioYaExisteNoDebeCrearUsuario() {
        // Arrange
        User user = new User();
        user.setUserRut("12345678-9");
        user.setUserLastName("nuñez");
        user.setUserName("felipe");
        user.setUserEmail("felipe.nuñez@ejemplo.com");
        user.setUserPhone("111111111");
        user.setUserPassword("123");
        user.setUserRole("ADMINISTRATIVE");

        when(userRepository.findByUserRut("12345678-9")).thenReturn(Optional.of(user)); // Mock del usuario existente

        // Act
        boolean resultado = userService.createUser(user);

        // Assert
        assertFalse(resultado); // El usuario no debe ser creado
        verify(userRepository, times(0)).save(user); // No se debe llamar a save
    }
 
    // Test para validación de login correcto
    @Test
    void siPasswordEsCorrectaLoginDebeRetornarTrue() {
        // Arrange
        User user = new User();
        user.setUserRut("12345678-9");
        user.setUserPassword("password123");
        when(userRepository.findByUserRut("12345678-9")).thenReturn(Optional.of(user));

        // Act
        boolean resultado = userService.validateLogin("12345678-9", "password123");

        // Assert
        assertTrue(resultado); // Verifica que el login es válido
    }

    // Test para verificar la actualización de rol de usuario
    @Test
    void siRolEsValidoUpdateUserRoleDebeActualizar() {
        // Arrange
        User user = new User();
        user.setUserRut("12345678-9");
        user.setUserRole("ESTUDIANTE");
        when(userRepository.findByUserRut("12345678-9")).thenReturn(Optional.of(user));

        // Act
        boolean resultado = userService.updateUserRole("12345678-9", "DESARROLLADOR");

        // Assert
        assertTrue(resultado); // Verifica que el rol se ha actualizado
        assertEquals("DESARROLLADOR", user.getUserRole()); // El nuevo rol debe ser DESARROLLADOR
        verify(userRepository, times(1)).save(user); // Verifica que se guarda el cambio
    }

    // Test para verificar que no se actualiza con rol inválido
    @Test
    void siRolNoEsValidoUpdateUserRoleDebeRetornarFalse() {
        // Arrange
        User user = new User();
        user.setUserRut("12345678-9");
        user.setUserRole("ESTUDIANTE");
        when(userRepository.findByUserRut("12345678-9")).thenReturn(Optional.of(user));

        // Act
        boolean resultado = userService.updateUserRole("12345678-9", "INVALID_ROLE");

        // Assert
        assertFalse(resultado); // Verifica que el rol no se actualiza
        assertEquals("ESTUDIANTE", user.getUserRole()); // El rol debe permanecer como ESTUDIANTE
        verify(userRepository, times(0)).save(user); // No se debe llamar a save
    }
}
