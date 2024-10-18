package com.Polo.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Postulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulation", nullable = false)
    private Integer id;

    @Basic

    @Column(name = "postulation_name", nullable = false)
    private String postulationName;

    @Column(name = "postulation_description", nullable = false, columnDefinition = "TEXT")
    private String postulationDescription;

    // Falta relacion ManyToMany con User y con Project
}
