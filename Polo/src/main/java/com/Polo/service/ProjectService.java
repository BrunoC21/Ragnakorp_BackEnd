package com.Polo.service;

import com.Polo.Details.ProjectUserDetailsService;
import com.Polo.model.*;
import com.Polo.repository.ProjectRepository;
// import com.Polo.repository.UserRepository;
import com.Polo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectUserDetailsService projectUserDetailsService;

    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

    public boolean createProject(Project project, String userRut) {
        if (project != null) {
            Optional<Project> existingProject = projectRepository.findByProjName(project.getProjName());
            if (existingProject.isPresent()) {
                System.out.println("El proyecto ya existe.");
                return false;
            } else {
                projectRepository.save(project);
                projectUserDetailsService.saveDetails(project, userRut);

                System.out.println("Proyecto creado exitosamente.");
                return true;
            }
        }
        return false;
    }

    public List<ProjectDTO> findAllProjects() {
        List<Project> projectList = projectRepository.findAll();
        List<ProjectDTO> projectDTOList;
        projectDTOList = mapper.projectListToProjectDTOList(projectList);
        System.out.println("todos los proyectos encontrados");
        return projectDTOList;
    }

    public Optional<ProjectDTO> findByProjectId(int id) {
        Optional<Project> optional = projectRepository.findById(id);
        if (optional.isPresent()) {
            System.out.println("Proyecto encontrado");
            return Optional.of(mapper.projectToProjectDTO(optional.get()));
        }
        System.out.println("Proyecto no encontrado");
        return Optional.empty();
    }

    // busqueda de projecto por nombre
    public Optional<ProjectDTO> findByProjName(String projName) {
        Optional<Project> optional = projectRepository.findByProjName(projName);
        if (optional.isPresent()) {
            System.out.println("Proyecto encontrado");
            return Optional.of(mapper.projectToProjectDTO(optional.get()));
        }
        System.out.println("Proyecto no encontrado");
        return Optional.empty();
    }

    public boolean deleteProject(int id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            System.out.println("Proyecto eliminado");
            return true;
        }
        System.out.println("Proyecto no eliminado");
        return false;
    } 

    public List<ProjectDTO> getProjectsCreatedByUser(String userRut) {
        User user = userRepository.findByUserRut(userRut).orElseThrow(() -> new RuntimeException("Usuario no encontrado con RUT: " + userRut));

        System.out.println("ENTRASTE A BUSCAR LOS PROYECTOS");
        
        return user.getProjects().stream()
                .map(project -> {
                    ProjectDTO dto = new ProjectDTO();
                    dto.setId(project.getId());
                    dto.setProjName(project.getProjName());
                    dto.setProjDescription(project.getProjDescription());
                    dto.setProjLong(project.getProjLong());
                    dto.setProjStartDate(project.getProjStartDate());
                    dto.setProjRequirementsPostulation(project.getProjRequirementsPostulation());
                    dto.setProjLat(project.getProjLat());
                    dto.setProjBudget(project.getProjBudget());
                    dto.setProjCategory(project.getProjCategory());
                    dto.setProjAddress(project.getProjAddress());
                    return dto;
                }).collect(Collectors.toList());

    }


    public boolean updateProject(ProjectDTO projectDTO, String rut) {
        try {
            // Buscar el proyecto existente
            Optional<Project> existingProject = projectRepository.findById(projectDTO.getId());
            if (!existingProject.isPresent()) {
                return false;
            }
    
            Project project = existingProject.get();
    
            // Actualizar los datos del proyecto
            project.setProjName(projectDTO.getProjName());
            project.setProjDescription(projectDTO.getProjDescription());
            project.setProjLong(projectDTO.getProjLong());
            project.setProjStartDate(projectDTO.getProjStartDate());
            project.setProjRequirementsPostulation(projectDTO.getProjRequirementsPostulation());
            project.setProjLat(projectDTO.getProjLat());
            project.setProjBudget(projectDTO.getProjBudget());
            project.setProjCategory(projectDTO.getProjCategory());
            project.setProjAddress(projectDTO.getProjAddress());
    
            // Si se proporcionó una nueva imagen, actualizarla
            if (projectDTO.getProjPicture() != null && !projectDTO.getProjPicture().isEmpty()) {
                project.setProjPicture(projectDTO.getProjPicture());
            }
    
            // Guardar los cambios
            projectRepository.save(project);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
