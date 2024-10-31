package com.Polo.service;

import com.Polo.model.Polocenter;
import com.Polo.repository.PoloCenterRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PoloCenterService {
    private final PoloCenterRepository poloCenterRepository;

    public boolean createPoloCenter(Polocenter poloCenter) {
        if (poloCenter != null) {
            Polocenter existingPoloCenter = poloCenterRepository.findPoloCenterByCenterName(poloCenter.getCenterName());
            if (existingPoloCenter != null) {
                System.out.println("El centro ya existe.");
                return false;
            } else {
                poloCenterRepository.save(poloCenter);
                System.out.println("Centro creado exitosamente.");
                return true;
            }
        }
        return false;
    }

    public List<Polocenter> findAllPoloCenters() {
        return poloCenterRepository.findAll();
    }

    public boolean deletePoloCenter(int id) {
        if (poloCenterRepository.existsById(id)) {
            poloCenterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Polocenter> findPoloCenterByCenterName(String centerName) {
        return Optional.ofNullable(poloCenterRepository.findPoloCenterByCenterName(centerName));
    }

}
