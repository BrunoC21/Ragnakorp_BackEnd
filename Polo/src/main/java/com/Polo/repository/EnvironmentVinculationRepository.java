package com.Polo.repository;

import com.Polo.model.EnvironmentVinculation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentVinculationRepository extends JpaRepository<EnvironmentVinculation, Integer> {
    Optional<EnvironmentVinculation> findByActivityName(String environmentVinculationName);
    List<EnvironmentVinculation> findTop3ByOrderByIdDesc();
    List<EnvironmentVinculation> findByActivityCategory(String environmentVinculationCategory);
}