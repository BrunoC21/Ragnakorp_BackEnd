package com.Polo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Polo.model.Suscription;

@Repository
public interface SuscriptionRepository extends JpaRepository<Suscription, Integer> {

    Suscription findBySubEmail(String subEmail);

}
