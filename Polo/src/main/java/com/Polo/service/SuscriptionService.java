package com.Polo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Polo.model.Suscription;
import com.Polo.repository.SuscriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuscriptionService {

    private final SuscriptionRepository suscriptionRepository;

    public boolean createSuscription(Suscription suscription) {
       if (suscription != null) {
            Suscription existingSuscription = suscriptionRepository.findBySubEmail(suscription.getSubEmail());

            if (existingSuscription != null) {
                System.out.println("Ya se encuentra suscrito");
                return false;
            } else {
                suscriptionRepository.save(suscription);
                System.out.println("Suscripcion confirmada");
                return true;
            }
       } else {
        System.out.println("Error al iniciar la suscripcion");
        return false;
       }
    }

    public List<Suscription> findAllSuscriptions() {
        return suscriptionRepository.findAll();
    }

    public Optional<Suscription> findSuscriptionsByEmail(String subEmail) {
        return Optional.ofNullable(suscriptionRepository.findBySubEmail(subEmail));
    }

    public boolean deleteSuscriptorByMail(String subEmail) {
        Suscription sub = suscriptionRepository.findBySubEmail(subEmail);
        
        if (sub != null) {
            suscriptionRepository.delete(sub);
            return true;
        }
        return false;
    }

}
