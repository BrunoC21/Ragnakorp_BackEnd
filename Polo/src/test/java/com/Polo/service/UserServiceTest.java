package com.Polo.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Polo.model.User;
import com.Polo.model.UserDTO;
import com.Polo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void siInvocoFindUsuariosYExistenUsuariosDebeRetornarListaUsuario() {
        // Arrange
        List<User> usuarios = getListaUsers(); // Crea una lista de usuarios de prueba
        when(userRepository.findAll()).thenReturn(usuarios);

        // Act
        List<UserDTO> resultado = userService.findAllUsers(); // Llama al método de servicio

        // Assert
        assertNotNull(resultado); // Verifica que la lista no sea nula
        assertEquals(usuarios.size(), resultado.size()); // Verifica que el tamaño sea el esperado

        // Verifica que los datos de los usuarios sean los correctos
        assertEquals(usuarios.get(0).getId(), resultado.get(0).getId());
        assertEquals(usuarios.get(1).getId(), resultado.get(1).getId());
    }

    // Método que genera datos de prueba
    private List<User> getListaUsers() {
        List<User> usuarios = new ArrayList<>();

        User user1 = new User();
        user1.setUserRut("22222222-2");
        user1.setUserLastName("Pérez");
        user1.setUserName("Juan");
        user1.setUserEmail("juan.perez@ejemplo.com");
        user1.setUserPhone("111111111");
        user1.setUserPassword("123");
        user1.setUserRole("ADMINISTRATIVE");
        usuarios.add(user1);

        User user2 = new User();
        user1.setUserRut("33333333-3");
        user1.setUserLastName("nuñez");
        user1.setUserName("felipe");
        user1.setUserEmail("felipe.nuñez@ejemplo.com");
        user1.setUserPhone("111111111");
        user1.setUserPassword("123");
        user1.setUserRole("ADMINISTRATIVE");
        usuarios.add(user2);

        return usuarios;
    }
}
