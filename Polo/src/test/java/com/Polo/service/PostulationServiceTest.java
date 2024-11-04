package com.Polo.service;

import java.util.List;
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
import org.mapstruct.factory.Mappers;

import com.Polo.model.Postulation;
import com.Polo.model.PostulationDTO;
import com.Polo.model.PostulationMapper;
import com.Polo.repository.PostulationRepository;
import com.Polo.Details.PostulationProjectDetailsService;
import com.Polo.Details.PostulationUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class PostulationServiceTest {

    @InjectMocks
    private PostulationService postulationService;

    @Mock
    private PostulationRepository postulationRepository;

    @Mock
    private PostulationUserDetailsService postulationUserDetailsService;

    @Mock
    private PostulationProjectDetailsService postulationProjectDetailsService;

    @Mock
    private PostulationMapper postulationMapper = Mappers.getMapper(PostulationMapper.class);

    @Test
    void siPostulationNoExisteDebeCrearPostulation() {
        Postulation postulation = new Postulation();
        postulation.setPostulationName("Tengo Fe");
        postulation.setPostulationDescription("Denme practica, o chamba, si pagan mejor.");
        postulation.setPostulationRut("20629292-k");
        postulation.setPostulationProject("Proyecto Salud saludable");

        when(postulationRepository.findById(postulation.getId())).thenReturn(Optional.empty());

        boolean resultado = postulationService.createPostulation(postulation);

        assertTrue(resultado);
        verify(postulationRepository, times(1)).save(postulation);
        verify(postulationUserDetailsService, times(1)).saveDetails(postulation);
        verify(postulationProjectDetailsService, times(1)).saveDetails(postulation);
    }

    @Test
    void siPostulationYaExisteNoDebeCrearPostulation() {
        Postulation postulation = new Postulation();
        postulation.setPostulationName("Tengo Fe");
        postulation.setPostulationDescription("Denme practica, o chamba, si pagan mejor.");
        postulation.setPostulationRut("20629292-k");
        postulation.setPostulationProject("Proyecto Salud saludable");

        when(postulationRepository.findById(postulation.getId())).thenReturn(Optional.of(postulation));

        boolean resultado = postulationService.createPostulation(postulation);

        assertFalse(resultado);
        verify(postulationRepository, times(0)).save(postulation);
    }

    @Test
    void debeRetornarListaDePostulations() {
        List<Postulation> postulationList = List.of(new Postulation(), new Postulation());
        List<PostulationDTO> postulationDTOList = List.of(new PostulationDTO(), new PostulationDTO());

        when(postulationRepository.findAll()).thenReturn(postulationList);
        when(postulationMapper.postulationListToPostulationDTOList(postulationList)).thenReturn(postulationDTOList);

        List<PostulationDTO> resultado = postulationService.findAllPostulations();

        assertEquals(2, resultado.size());
        verify(postulationRepository, times(1)).findAll();
    }

    @Test
    void debeRetornarPostulationPorId() {
        Postulation postulation = new Postulation();
        postulation.setId(1);
        PostulationDTO postulationDTO = new PostulationDTO();

        when(postulationRepository.findById(1)).thenReturn(Optional.of(postulation));
        when(postulationMapper.postulationToPostulationDTO(postulation)).thenReturn(postulationDTO);

        Optional<PostulationDTO> resultado = postulationService.findPostulationById(1);

        assertTrue(resultado.isPresent());
        assertEquals(postulationDTO, resultado.get());
        verify(postulationRepository, times(1)).findById(1);
    }

    @Test
    void siPostulationNoExisteDebeRetornarEmptyAlBuscarPorId() {
        when(postulationRepository.findById(99)).thenReturn(Optional.empty());

        Optional<PostulationDTO> resultado = postulationService.findPostulationById(99);

        assertFalse(resultado.isPresent());
        verify(postulationRepository, times(1)).findById(99);
    }

    @Test
    void siPostulationExisteDebeEliminarPostulation() {
        int postulationId = 1;

        when(postulationRepository.existsById(postulationId)).thenReturn(true);

        boolean resultado = postulationService.deletePostulation(postulationId);

        assertTrue(resultado);
        verify(postulationRepository, times(1)).deleteById(postulationId);
    }

    @Test
    void siPostulationNoExisteNoDebeEliminarPostulation() {
        int postulationId = 99;

        when(postulationRepository.existsById(postulationId)).thenReturn(false);

        boolean resultado = postulationService.deletePostulation(postulationId);

        assertFalse(resultado);
        verify(postulationRepository, times(0)).deleteById(postulationId);
    }
}
