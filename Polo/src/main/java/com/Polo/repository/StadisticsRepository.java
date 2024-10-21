package com.Polo.repository;

import com.Polo.model.Stadistics;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StadisticsRepository  extends JpaRepository<Stadistics, Integer> {

}
