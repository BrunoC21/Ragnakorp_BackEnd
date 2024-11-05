package com.Polo.service;

import com.Polo.Details.PostulationProjectDetailsService;
import com.Polo.Details.PostulationUserDetailsService;
import com.Polo.model.Postulation;
import com.Polo.model.PostulationDTO;
import com.Polo.model.PostulationMapper;
import com.Polo.repository.PostulationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostulationServiceTest {

    @Mock
    private PostulationRepository postulationRepository;

    @Mock
    private PostulationUserDetailsService postulationUserDetailsService;

    @Mock
    private PostulationProjectDetailsService postulationProjectDetailsService;

    @Mock
    private PostulationMapper postulationMapper = Mappers.getMapper(PostulationMapper.class);

    @InjectMocks
    private PostulationService postulationService;

    // Test para verificar la creación de una postulación
    @Test
    void siPostulacionNoExisteDebeCrearPostulacion() {
        // Arrange
        Postulation postulation = new Postulation();
        postulation.setId(1);
        postulation.setPostulationName("Proyecto Ejemplo");
        postulation.setPostulationRut("12345678-9");
        postulation.setPostulationDescription("Descripción de ejemplo para la postulación.");
        postulation.setPostulationProject("Proyecto de Innovación");

        when(postulationRepository.save(postulation)).thenReturn(postulation);

        // Act
        boolean resultado = postulationService.createPostulation(postulation);

        // Assert
        assertTrue(resultado); // La postulación debe ser creada
        verify(postulationRepository, times(1)).save(postulation); // Verifica que se llame a save
    }

    @Test
    void siPostulacionExistePorIdDebeRetornarDTO() {
        // Arrange
        Postulation postulation = new Postulation();
        postulation.setId(1);
        postulation.setPostulationName("Proyecto Ejemplo");
        postulation.setPostulationRut("12345678-9");
        postulation.setPostulationDescription("Descripción de ejemplo para la postulación.");
        postulation.setPostulationProject("Proyecto de Innovación");

        PostulationDTO postulationDTO = new PostulationDTO();
        postulationDTO.setId(1);
        postulationDTO.setPostulationName("Proyecto Ejemplo");
        postulationDTO.setPostulationRut("12345678-9");
        postulationDTO.setPostulationDescription("Descripción de ejemplo para la postulación.");
        postulationDTO.setPostulationProject("Proyecto de Innovación");

        lenient().when(postulationRepository.findById(1)).thenReturn(Optional.of(postulation));
        lenient().when(postulationMapper.postulationToPostulationDTO(postulation)).thenReturn(postulationDTO);

        // Act
        Optional<PostulationDTO> resultado = postulationService.findPostulationById(1);

        // Assert
        assertTrue(resultado.isPresent()); // Verifica que se encontró la postulación
        assertEquals(postulationDTO, resultado.get()); // Verifica que el DTO es correcto
    }

    @Test
    void siPostulacionNoExistePorIdDebeRetornarEmpty() {
        // Arrange
        when(postulationRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<PostulationDTO> resultado = postulationService.findPostulationById(1);

        // Assert
        assertFalse(resultado.isPresent()); // Verifica que no se encontró la postulación
    }

    @Test
    void debeRetornarListaDePostulacionesDTO() {
        // Arrange
        Postulation postulation = new Postulation();
        postulation.setId(1);
        postulation.setPostulationName("Proyecto Ejemplo");
        postulation.setPostulationRut("12345678-9");
        postulation.setPostulationDescription("Descripción de ejemplo para la postulación.");
        postulation.setPostulationProject("Proyecto de Innovación");

        PostulationDTO postulationDTO = new PostulationDTO();
        postulationDTO.setId(1);
        postulationDTO.setPostulationName("Proyecto Ejemplo");
        postulationDTO.setPostulationRut("12345678-9");
        postulationDTO.setPostulationDescription("Descripción de ejemplo para la postulación.");
        postulationDTO.setPostulationProject("Proyecto de Innovación");

        List<Postulation> postulationList = List.of(postulation);
        List<PostulationDTO> postulationDTOList = List.of(postulationDTO);

        lenient().when(postulationRepository.findAll()).thenReturn(postulationList);
        lenient().when(postulationMapper.postulationListToPostulationDTOList(postulationList))
                .thenReturn(postulationDTOList);

        // Act
        List<PostulationDTO> resultado = postulationService.findAllPostulations();

        // Assert
        assertEquals(postulationDTOList, resultado); // Verifica que el resultado es igual a la lista de DTO esperada
    }

    @Test
    void siPostulacionExisteDebeEliminarla() {
        // Arrange
        int postulationId = 1;
        when(postulationRepository.existsById(postulationId)).thenReturn(true);

        // Act
        boolean resultado = postulationService.deletePostulation(postulationId);

        // Assert
        assertTrue(resultado); // Verifica que la postulación se ha eliminado
        verify(postulationRepository, times(1)).deleteById(postulationId); // Verifica que se llamó a deleteById
    }

    @Test
    void siPostulacionNoExisteNoDebeEliminarla() {
        // Arrange
        int postulationId = 1;
        when(postulationRepository.existsById(postulationId)).thenReturn(false);

        // Act
        boolean resultado = postulationService.deletePostulation(postulationId);

        // Assert
        assertFalse(resultado); // Verifica que no se eliminó la postulación
        verify(postulationRepository, times(0)).deleteById(postulationId); // Verifica que no se llamó a deleteById
    }

}
