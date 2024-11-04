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

import com.Polo.model.Project;
import com.Polo.model.ProjectDTO;
import com.Polo.model.ProjectMapper;
import com.Polo.repository.ProjectRepository;
import com.Polo.Details.ProjectUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectUserDetailsService projectUserDetailsService;

    @Mock
    private ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    @Test
    void siProjectNoExisteDebeCrearProject() {
        Project project = new Project();
        project.setProjName("Proyecto Innovador");
        String userRut = "12345678-9";
        project.setProjDescription("Projecto Saludable salud");
        project.setProjLong("19° 25 42 N");
        project.setProjLat("99° 7 39 O");
        project.setProjStartDate("30 de noviembre de 2024");
        project.setProjBudget("4000000000");
        project.setProjCategory("Externo");
        
        when(projectRepository.findByProjName("Proyecto Innovador")).thenReturn(Optional.empty());

        boolean resultado = projectService.createProject(project, userRut);

        assertTrue(resultado);
        verify(projectRepository, times(1)).save(project);
        verify(projectUserDetailsService, times(1)).saveDetails(project, userRut);
    }

    @Test
    void siProjectYaExisteNoDebeCrearProject() {
        Project project = new Project();
        project.setProjName("Proyecto Existente");
        String userRut = "12345678-9";
        project.setProjDescription("Projecto Saludable salud");
        project.setProjLong("19° 25 42 N");
        project.setProjLat("99° 7 39 O");
        project.setProjStartDate("30 de noviembre de 2024");
        project.setProjBudget("4000000000");
        project.setProjCategory("Externo");

        when(projectRepository.findByProjName("Proyecto Existente")).thenReturn(Optional.of(project));

        boolean resultado = projectService.createProject(project, userRut);

        assertFalse(resultado);
        verify(projectRepository, times(0)).save(project);
    }

    @Test
    void debeRetornarListaDeProjects() {
        List<Project> projectList = List.of(new Project(), new Project());
        List<ProjectDTO> projectDTOList = List.of(new ProjectDTO(), new ProjectDTO());

        when(projectRepository.findAll()).thenReturn(projectList);
        when(projectMapper.projectListToProjectDTOList(projectList)).thenReturn(projectDTOList);

        List<ProjectDTO> resultado = projectService.findAllProjects();

        assertEquals(2, resultado.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void debeRetornarProjectPorId() {
        Project project = new Project();
        project.setId(1);
        ProjectDTO projectDTO = new ProjectDTO();

        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(projectMapper.projectToProjectDTO(project)).thenReturn(projectDTO);

        Optional<ProjectDTO> resultado = projectService.findByProjectId(1);

        assertTrue(resultado.isPresent());
        assertEquals(projectDTO, resultado.get());
        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    void siProjectNoExisteDebeRetornarEmptyAlBuscarPorId() {
        when(projectRepository.findById(99)).thenReturn(Optional.empty());

        Optional<ProjectDTO> resultado = projectService.findByProjectId(99);

        assertFalse(resultado.isPresent());
        verify(projectRepository, times(1)).findById(99);
    }

    @Test
    void debeRetornarProjectPorNombre() {
        Project project = new Project();
        project.setProjName("Proyecto Salud");
        ProjectDTO projectDTO = new ProjectDTO();

        when(projectRepository.findByProjName("Proyecto Salud")).thenReturn(Optional.of(project));
        when(projectMapper.projectToProjectDTO(project)).thenReturn(projectDTO);

        Optional<ProjectDTO> resultado = projectService.findByProjName("Proyecto Salud");

        assertTrue(resultado.isPresent());
        assertEquals(projectDTO, resultado.get());
        verify(projectRepository, times(1)).findByProjName("Proyecto Salud");
    }

    @Test
    void siProjectExisteDebeEliminarProject() {
        int projectId = 1;

        when(projectRepository.existsById(projectId)).thenReturn(true);

        boolean resultado = projectService.deleteProject(projectId);

        assertTrue(resultado);
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void siProjectNoExisteNoDebeEliminarProject() {
        int projectId = 99;

        when(projectRepository.existsById(projectId)).thenReturn(false);

        boolean resultado = projectService.deleteProject(projectId);

        assertFalse(resultado);
        verify(projectRepository, times(0)).deleteById(projectId);
    }
}

