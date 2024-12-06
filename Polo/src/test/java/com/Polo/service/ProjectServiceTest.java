package com.Polo.service;

import com.Polo.Details.ProjectUserDetailsService;
import com.Polo.model.*;
import com.Polo.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectUserDetailsService projectUserDetailsService;

    private ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    @SuppressWarnings("unused")
    private ProjectDTO projectDTO;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(1);
        project.setProjName("Project Name");
        project.setProjDescription("Description");
        project.setProjLong("Long");
        project.setProjStartDate("2024-01-01");
        project.setProjRequirementsPostulation("Requirements");
        project.setProjLat("Lat");
        project.setProjBudget("Budget");
        project.setProjCategory("Category");

        projectDTO = projectMapper.projectToProjectDTO(project);
    }

    @Test
    void whenCreateProject_thenReturnTrue() {
        // Arrange
        when(projectRepository.findByProjName(project.getProjName())).thenReturn(Optional.empty());
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Act
        boolean result = projectService.createProject(project, "userRut");

        // Assert
        assertTrue(result);
        verify(projectRepository, times(1)).save(project);
        verify(projectUserDetailsService, times(1)).saveDetails(project, "userRut");
    }

    @Test
    void whenCreateProjectAndProjectExists_thenReturnFalse() {
        // Arrange
        when(projectRepository.findByProjName(project.getProjName())).thenReturn(Optional.of(project));

        // Act
        boolean result = projectService.createProject(project, "userRut");

        // Assert
        assertFalse(result);
        verify(projectRepository, never()).save(project);
    }

    @Test
    void whenFindAllProjects_thenReturnProjectDTOList() {
        // Arrange
        List<Project> projects = List.of(project);
        when(projectRepository.findAll()).thenReturn(projects);

        // Act
        List<ProjectDTO> result = projectService.findAllProjects();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(project.getProjName(), result.get(0).getProjName());
    }

    @Test
    void whenFindByProjectId_thenReturnProjectDTO() {
        // Arrange
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));

        // Act
        Optional<ProjectDTO> result = projectService.findByProjectId(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(project.getProjName(), result.get().getProjName());
    }

    @Test
    void whenFindByProjectIdAndProjectNotFound_thenReturnEmpty() {
        // Arrange
        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<ProjectDTO> result = projectService.findByProjectId(1);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void whenFindByProjName_thenReturnProjectDTO() {
        // Arrange
        when(projectRepository.findByProjName("Project Name")).thenReturn(Optional.of(project));

        // Act
        Optional<ProjectDTO> result = projectService.findByProjName("Project Name");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(project.getProjName(), result.get().getProjName());
    }

    @Test
    void whenDeleteProject_thenReturnTrue() {
        // Arrange
        when(projectRepository.existsById(1)).thenReturn(true);

        // Act
        boolean result = projectService.deleteProject(1);

        // Assert
        assertTrue(result);
        verify(projectRepository, times(1)).deleteById(1);
    }

    @Test
    void whenDeleteProjectAndProjectNotExists_thenReturnFalse() {
        // Arrange
        when(projectRepository.existsById(1)).thenReturn(false);

        // Act
        boolean result = projectService.deleteProject(1);

        // Assert
        assertFalse(result);
        verify(projectRepository, never()).deleteById(1);
    }
}
