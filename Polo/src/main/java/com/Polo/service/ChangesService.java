package com.Polo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.Polo.Details.NewsChangesDetailsService;
import com.Polo.model.Changes;
import com.Polo.model.ChangesDTO;
import com.Polo.model.ChangesMapper;
import com.Polo.repository.ChangesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangesService {

    private final ChangesRepository changesRepository;
    private final NewsChangesDetailsService newsChangesDetailsService;

    private final ChangesMapper mapper = Mappers.getMapper(ChangesMapper.class);

    
    public boolean createChange(Changes changes) {
        if (changes != null) {
            changesRepository.save(changes);
            newsChangesDetailsService.saveDetails(changes);
            return true;
        }
        return false;
    }

    public Optional<List<ChangesDTO>> findAllNewsChanges() {
        List<Changes> changesList = changesRepository.findAllByChangesDescription("Noticia");
        if (changesList != null && !changesList.isEmpty()) {
            System.out.println("Encontrado");
            return Optional.of(changesList.stream().map(mapper::changesToChangesDTO).collect(Collectors.toList()));
        }
        System.out.println("No encontrado");
        return Optional.empty();
    }

}