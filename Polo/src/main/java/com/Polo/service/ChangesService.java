package com.Polo.service;

import org.springframework.stereotype.Service;

import com.Polo.Details.NewsChangesDetailsService;
import com.Polo.model.Changes;
import com.Polo.repository.ChangesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangesService {

    private final ChangesRepository changesRepository;
    private final NewsChangesDetailsService newsChangesDetailsService;
    
    public boolean createChange(Changes changes) {
        if (changes != null) {
            changesRepository.save(changes);
            newsChangesDetailsService.saveDetails(changes);
            return true;
        }
        return false;
    }

}