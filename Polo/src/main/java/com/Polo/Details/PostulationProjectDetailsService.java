package com.Polo.Details;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Polo.model.Postulation;
import com.Polo.model.Project;
import com.Polo.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulationProjectDetailsService {

    private final ProjectRepository projectRepository;

    public void saveDetails(Postulation postulation) {
        String postulationProjectName = postulation.getPostulationProject();

        Optional<Project> project = projectRepository.findByProjName(postulationProjectName);
        if (project != null) {
            if (project.get().getPostulationProj() == null) {
                project.get().setPostulationProj(new ArrayList<>());
                projectRepository.save(project.get());
            }

            if (!project.get().getPostulationProj().contains(postulation)) {
                project.get().getPostulationProj().add(postulation);
            }
        } else {
            System.out.println("Usuario no encontrado con el nombre: " + postulationProjectName);
        }

    }

}
