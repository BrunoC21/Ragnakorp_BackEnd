package com.Polo.Details;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.Polo.model.Changes;
import com.Polo.model.News;
import com.Polo.model.Project;
import com.Polo.repository.ChangesRepository;
import com.Polo.repository.NewsRepository;
import com.Polo.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsChangesDetailsService {

    private final NewsRepository newsRepository;
    private final ChangesRepository changesRepository;
    private final ProjectRepository projectRepository;

    public void saveDetails(Changes changes) {
        // Convertir los IDs a Integer
        Integer idElementoInt = Integer.parseInt(changes.getChangesThing());
        Integer idChangeInt = changes.getId();
        String changesDescription = changes.getChangesDescription();
    
        if (changesDescription.equals("Noticia")) {
            // Buscar las entidades
            Optional<News> newsOptional = newsRepository.findById(idElementoInt);
            Optional<Changes> changesOptional = changesRepository.findById(idChangeInt);
        
            if (changesOptional.isPresent()) {
                Changes existingChange = changesOptional.get();
        
                // Inicializar la lista de relaciones si es null
                if (existingChange.getNewsChange() == null) {
                    existingChange.setNewsChange(new ArrayList<>());
                }
        
                // Verificar si la noticia existe
                if (newsOptional.isPresent()) {
                    News news = newsOptional.get();
        
                    // Añadir la noticia a la relación si no está ya presente
                    existingChange.getNewsChange().add(news);
    
                    // Guardar los cambios en la base de datos
                    changesRepository.save(existingChange);
                } else {
                    System.out.println("Noticia no encontrada.");
                }
            } else {
                System.out.println("Cambio no encontrado.");
            }
        } else {
            Optional<Project> projectOptional = projectRepository.findById(idElementoInt);
            Optional<Changes> changesOptional = changesRepository.findById(idChangeInt);
        
            if (changesOptional.isPresent()) {
                Changes existingChange = changesOptional.get();
        
                // Inicializar la lista de relaciones si es null
                if (existingChange.getProjectChange() == null) {
                    existingChange.setProjectChange(new ArrayList<>());
                }
        
                // Verificar si la noticia existe
                if (projectOptional.isPresent()) {
                    Project project = projectOptional.get();
        
                    // Añadir la noticia a la relación si no está ya presente
                    existingChange.getProjectChange().add(project);
                    // Guardar los cambios en la base de datos
                    changesRepository.save(existingChange);
                } else {
                    System.out.println("Noticia no encontrada.");
                }
            } else {
                System.out.println("Cambio no encontrado.");
            }
        }
    }
    

}
