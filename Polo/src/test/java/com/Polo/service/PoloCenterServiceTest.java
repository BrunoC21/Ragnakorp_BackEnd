package com.Polo.service;

import com.Polo.model.Polocenter;
import com.Polo.repository.PoloCenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PoloCenterServiceTest {

    @Mock
    private PoloCenterRepository poloCenterRepository;


    @InjectMocks
    private PoloCenterService poloCenterService;

    private Polocenter poloCenter;

    @BeforeEach
    void setUp() {
        poloCenter = new Polocenter();
        poloCenter.setId(1);
        poloCenter.setCenterName("Centro Polo");
        poloCenter.setCenterDescription("Descripción del centro");
        poloCenter.setCenterLocation("Ubicación");
        poloCenter.setCenterDirection("Dirección");
        poloCenter.setCenterContact("Contacto");
    }

    @Test
    void cuandoCreatePoloCenterYNoExisteCentroDebeCrearCentro() {
        // Arrange
        when(poloCenterRepository.findPoloCenterByCenterName(poloCenter.getCenterName())).thenReturn(null);
        when(poloCenterRepository.save(any(Polocenter.class))).thenReturn(poloCenter);

        // Act
        boolean resultado = poloCenterService.createPoloCenter(poloCenter);

        // Assert
        assertTrue(resultado);
        verify(poloCenterRepository, times(1)).save(poloCenter);
    }

    @Test
    void cuandoCreatePoloCenterYCentroExisteNoDebeCrearCentro() {
        // Arrange
        when(poloCenterRepository.findPoloCenterByCenterName(poloCenter.getCenterName())).thenReturn(poloCenter);

        // Act
        boolean resultado = poloCenterService.createPoloCenter(poloCenter);

        // Assert
        assertFalse(resultado);
        verify(poloCenterRepository, never()).save(poloCenter);
    }

    @Test
    void cuandoFindAllPoloCentersDebeRetornarListaDeCentros() {
        // Arrange
        List<Polocenter> centros = List.of(poloCenter);
        when(poloCenterRepository.findAll()).thenReturn(centros);

        // Act
        List<Polocenter> resultado = poloCenterService.findAllPoloCenters();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(poloCenter.getCenterName(), resultado.get(0).getCenterName());
    }

    @Test
    void cuandoDeletePoloCenterSiExisteDebeEliminarCentro() {
        // Arrange
        when(poloCenterRepository.existsById(1)).thenReturn(true);

        // Act
        boolean resultado = poloCenterService.deletePoloCenter(1);

        // Assert
        assertTrue(resultado);
        verify(poloCenterRepository, times(1)).deleteById(1);
    }

    @Test
    void cuandoDeletePoloCenterSiNoExisteDebeRetornarFalse() {
        // Arrange
        when(poloCenterRepository.existsById(1)).thenReturn(false);

        // Act
        boolean resultado = poloCenterService.deletePoloCenter(1);

        // Assert
        assertFalse(resultado);
        verify(poloCenterRepository, never()).deleteById(1);
    }

    @Test
    void cuandoFindPoloCenterByCenterNameSiExisteDebeRetornarCentro() {
        // Arrange
        when(poloCenterRepository.findPoloCenterByCenterName("Centro Polo")).thenReturn(poloCenter);

        // Act
        Optional<Polocenter> resultado = poloCenterService.findPoloCenterByCenterName("Centro Polo");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(poloCenter.getCenterName(), resultado.get().getCenterName());
    }

    @Test
    void cuandoFindPoloCenterByCenterNameSiNoExisteDebeRetornarVacio() {
        // Arrange
        when(poloCenterRepository.findPoloCenterByCenterName("Centro Inexistente")).thenReturn(null);

        // Act
        Optional<Polocenter> resultado = poloCenterService.findPoloCenterByCenterName("Centro Inexistente");

        // Assert
        assertTrue(resultado.isEmpty());
    }
}
