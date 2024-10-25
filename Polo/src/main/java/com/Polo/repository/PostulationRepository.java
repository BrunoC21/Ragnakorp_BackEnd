package com.Polo.repository;

import com.Polo.model.Postulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulationRepository extends JpaRepository<Postulation, Integer> {
    Postulation findPostulationById(int id);
}
