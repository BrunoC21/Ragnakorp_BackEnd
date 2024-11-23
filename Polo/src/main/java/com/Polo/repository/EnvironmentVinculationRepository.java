package com.Polo.repository;

import com.Polo.model.EnvironmentVinculation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentVinculationRepository extends JpaRepository<EnvironmentVinculation, Integer> {
    Optional<EnvironmentVinculation> findByActivityName(String environmentVinculationName);

}
