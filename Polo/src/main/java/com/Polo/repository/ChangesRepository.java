package com.Polo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Polo.model.Changes;

@Repository
public interface ChangesRepository extends JpaRepository<Changes, Integer> {

    List<Changes> findAllByChangesDescription(String changesDescription);

}
