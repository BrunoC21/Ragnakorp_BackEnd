package com.Polo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Polo.model.Access;
import com.Polo.model.AccessDTO;
import com.Polo.model.Stadistics;
import com.Polo.repository.AccessRepository;
import com.Polo.repository.StadisticsRepository;
import com.Polo.model.AccessMapper;

@Service
public class AccessService {

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private StadisticsRepository stadisticsRepository;

    @Autowired
    private AccessMapper accessMapper;

    @Transactional(readOnly = true)
    public List<AccessDTO> getAllAccess() {
        List<Access> accesses = accessRepository.findAll();
        return accesses.stream()
                .map(accessMapper::toDto)
                .toList();
    }

    private Stadistics createNewStadistics(LocalDateTime dateTime) {
        LocalDateTime startOfDay = dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        Stadistics stats = new Stadistics();
        stats.setAccessDay(startOfDay);
        stats.setTotalAccess(0);
        return stats;
    }

    private String parseDevice(String userAgent) {
        if (userAgent.toLowerCase().contains("mobile")) {
            return "Móvil";
        } else if (userAgent.toLowerCase().contains("tablet")) {
            return "Tablet";
        }
        return "Escritorio";
    }

    @Transactional
    public AccessDTO registerAccess(AccessDTO accessDTO) {
        // Obtener y simplificar el dispositivo
        String simplifiedDevice = parseDevice(accessDTO.getDevice());
        accessDTO.setDevice(simplifiedDevice);

        // Convertir DTO a entidad
        Access access = accessMapper.toEntity(accessDTO);

        // Lógica existente para estadística
        LocalDateTime today = LocalDateTime.now();
        Stadistics stats = stadisticsRepository.findByAccessDay(today)
                .orElseGet(() -> createNewStadistics(today));

        stats.setTotalAccess(stats.getTotalAccess() + 1);
        stadisticsRepository.save(stats);

        // Asociar la estadística con el acceso
        access.setStadistics(List.of(stats));

        // Guardar el acceso
        Access savedAccess = accessRepository.save(access);

        // Devolver el DTO
        return accessMapper.toDto(savedAccess);
    }

}
