package com.Polo.repository;

import com.Polo.model.Polocenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoloCenterRepository extends JpaRepository<Polocenter, Integer> {
    Polocenter findPoloCenterByCenterName(String centerName);
}
