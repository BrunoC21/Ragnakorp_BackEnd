package com.Polo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Polo.model.Stadistics;
import com.Polo.model.StadisticsDTO;
import com.Polo.repository.StadisticsRepository;
import com.Polo.model.StadisticsMapper;

@Service
public class StadisticsService {

    @Autowired
    private StadisticsRepository stadisticsRepository;

    @Autowired
    private StadisticsMapper stadisticsMapper;

    @Transactional
    public StadisticsDTO createStadistics(StadisticsDTO stadisticsDTO) {
        // Convertir DTO a entidad
        Stadistics stadistics = stadisticsMapper.toEntity(stadisticsDTO);
        // Guardar en la base de datos
        Stadistics savedStadistics = stadisticsRepository.save(stadistics);
        // Convertir de vuelta a DTO y retornar
        return stadisticsMapper.toDto(savedStadistics);
    }

    @Transactional(readOnly = true)
    public List<StadisticsDTO> getAllStadistics() {
        List<Stadistics> stadisticsList = stadisticsRepository.findAll();
        return stadisticsList.stream()
                .map(stadisticsMapper::toDto)
                .toList();
    }
}
