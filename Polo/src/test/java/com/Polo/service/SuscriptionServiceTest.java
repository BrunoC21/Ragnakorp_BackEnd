package com.Polo.service;

import com.Polo.model.Suscription;
import com.Polo.repository.SuscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SuscriptionServiceTest {

    @InjectMocks
    private SuscriptionService suscriptionService;

    @Mock
    private SuscriptionRepository suscriptionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void siSuscripcionEsNulaNoDeberiaCrear() {
        // Act
        boolean resultado = suscriptionService.createSuscription(null);

        // Assert
        assertFalse(resultado); // Debe retornar falso si la suscripción es nula
        verify(suscriptionRepository, times(0)).save(any()); // No debe llamar a save
    }

    @Test
    void siSuscripcionYaExisteNoDeberiaCrear() {
        // Arrange
        Suscription suscription = new Suscription();
        suscription.setSubEmail("test@example.com");
        
        when(suscriptionRepository.findBySubEmail("test@example.com")).thenReturn(suscription);

        // Act
        boolean resultado = suscriptionService.createSuscription(suscription);

        // Assert
        assertFalse(resultado); // Debe retornar falso si la suscripción ya existe
        verify(suscriptionRepository, times(0)).save(suscription); // No debe llamar a save
    }

    @Test
    void siSuscripcionNoExisteDeberiaCrear() {
        // Arrange
        Suscription suscription = new Suscription();
        suscription.setSubEmail("test@example.com");

        when(suscriptionRepository.findBySubEmail("test@example.com")).thenReturn(null);

        // Act
        boolean resultado = suscriptionService.createSuscription(suscription);

        // Assert
        assertTrue(resultado); // Debe retornar verdadero si la suscripción se creó
        verify(suscriptionRepository, times(1)).save(suscription); // Debe llamar a save
    }

    @Test
    void siInvocoFindAllSuscriptionsDeberiaRetornarLista() {
        // Arrange
        List<Suscription> suscriptions = new ArrayList<>();
        Suscription suscription1 = new Suscription();
        suscription1.setSubEmail("test1@example.com");
        Suscription suscription2 = new Suscription();
        suscription2.setSubEmail("test2@example.com");
        suscriptions.add(suscription1);
        suscriptions.add(suscription2);

        when(suscriptionRepository.findAll()).thenReturn(suscriptions);

        // Act
        List<Suscription> resultado = suscriptionService.findAllSuscriptions();

        // Assert
        assertNotNull(resultado); // Verifica que la lista no sea nula
        assertEquals(2, resultado.size()); // Verifica que el tamaño sea el esperado
    }

    @Test
    void siInvocoFindSuscriptionsByEmailYExisteDebeRetornarSuscripcion() {
        // Arrange
        Suscription suscription = new Suscription();
        suscription.setSubEmail("test@example.com");
        when(suscriptionRepository.findBySubEmail("test@example.com")).thenReturn(suscription);

        // Act
        Optional<Suscription> resultado = suscriptionService.findSuscriptionsByEmail("test@example.com");

        // Assert
        assertTrue(resultado.isPresent()); // Verifica que la suscripción se encuentra
        assertEquals(suscription, resultado.get()); // Verifica que sea la misma suscripción
    }

    @Test
    void siInvocoFindSuscriptionsByEmailYNoExisteNoDebeRetornarSuscripcion() {
        // Arrange
        when(suscriptionRepository.findBySubEmail("test@example.com")).thenReturn(null);

        // Act
        Optional<Suscription> resultado = suscriptionService.findSuscriptionsByEmail("test@example.com");

        // Assert
        assertFalse(resultado.isPresent()); // Verifica que no se encuentra la suscripción
    }

    @Test
    void siInvocoDeleteSuscriptorByMailYExisteDebeEliminar() {
        // Arrange
        Suscription suscription = new Suscription();
        suscription.setSubEmail("test@example.com");
        when(suscriptionRepository.findBySubEmail("test@example.com")).thenReturn(suscription);

        // Act
        boolean resultado = suscriptionService.deleteSuscriptorByMail("test@example.com");

        // Assert
        assertTrue(resultado); // Debe retornar verdadero si la suscripción se eliminó
        verify(suscriptionRepository, times(1)).delete(suscription); // Debe llamar a delete
    }

    @Test
    void siInvocoDeleteSuscriptorByMailYNoExisteNoDeberiaEliminar() {
        // Arrange
        when(suscriptionRepository.findBySubEmail("test@example.com")).thenReturn(null);

        // Act
        boolean resultado = suscriptionService.deleteSuscriptorByMail("test@example.com");

        // Assert
        assertFalse(resultado); // Debe retornar falso si no se eliminó
        verify(suscriptionRepository, times(0)).delete(any()); // No debe llamar a delete
    }
}
