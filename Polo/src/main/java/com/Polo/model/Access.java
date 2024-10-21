package com.Polo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acces", nullable = false)
    private Integer id;

    @Basic
    @CreationTimestamp
    @Column(name = "hour", nullable = false, updatable = false)
    private LocalDateTime hour;

    @Column(name = "device", nullable = false)
    private String device;

    // tabla relacion con stadistics
    @ManyToMany
    @JoinTable(name = "access_stadistic", // nombre tabla intermedia
            joinColumns = @JoinColumn(name = "id_acces"), // columna que hace referencia a usuario
            inverseJoinColumns = @JoinColumn(name = "id_stadistic")) // Columna que referencia a project
    private List<Stadistics> stadistics; // nombre asignado al mappeo

}
