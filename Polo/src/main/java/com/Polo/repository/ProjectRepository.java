package com.Polo.repository;

import com.Polo.model.Project;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByProjName(String projName);
}
