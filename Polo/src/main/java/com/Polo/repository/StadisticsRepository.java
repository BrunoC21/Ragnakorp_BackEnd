package com.Polo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Polo.model.Stadistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import com.Polo.model.Stadistics;

public interface StadisticsRepository extends JpaRepository<Stadistics, Integer> {
    Optional<Stadistics> findByAccessDay(LocalDateTime accessDay);
}
