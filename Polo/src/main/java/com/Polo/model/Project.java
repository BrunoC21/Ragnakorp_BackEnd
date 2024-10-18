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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proj_id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "proj_name", nullable = false)
    private String projName;

    @Column(name = "proj_description", nullable = false, columnDefinition = "TEXT")
    private String projDescription;

    @Column(name = "proj_long", nullable = false)
    private String projLong;

    @Column(name = "proj_startdate", nullable = false)
    private String projStartDate;

    @Column(name = "proj_requirementspostulation", nullable = false, columnDefinition = "TEXT")
    private String projRequirementsPostulation;

    @Column(name = "proj_lat", nullable = false)
    private String projLat;

    @Column(name = "proj_budget", nullable = false)
    private String projBudget;

    @Column(name = "proj_category", nullable = false)
    private String projCategory;

    // Falta relacion ManyToMany con User
}
