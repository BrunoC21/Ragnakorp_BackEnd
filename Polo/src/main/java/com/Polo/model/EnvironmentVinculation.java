package com.Polo.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class EnvironmentVinculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vinculation", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "activity_name", nullable = false)
    private String activityName;

    @Column(name = "activity_description", nullable = false, columnDefinition = "TEXT")
    private String activityDescription;

    @Column(name = "activity_category", nullable = false)
    private String activityCategory;

    @ManyToOne
    @JoinColumn(name = "activity_iduser", nullable = true) // Clave foránea
    private User user;
}
