package com.Polo.model;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @Column(name = "postulation_rut", nullable = false)
    private String postulationRut;

    @Column(name = "postulation_description", nullable = false, columnDefinition = "TEXT")
    private String postulationDescription;

    @Column(name = "postulation_project", nullable = false)
    private String postulationProject;

    @Column(name = "postulation_status", nullable = false)
    private String postulationStatus;

    // Relacion ManyToMany con User y con Project
    @ManyToMany(mappedBy = "postulation")
    private List<User> user;

    @ManyToMany(mappedBy = "postulationProj")
    private List<Project> project;
}
