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
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Changes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_changes", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "change_description", nullable = false, columnDefinition = "TEXT")
    private String changesDescription;

    @CreationTimestamp
    @Column(name = "change_date", nullable = false, updatable = false)
    private LocalDateTime changesDate;

    @Column(name = "change_thing", nullable = false, columnDefinition = "TEXT")
    private String changesThing;

    @Column(name = "change_type", nullable = false)
    private String changesType;

    @ManyToOne
    @JoinColumn(name = "change_iduser", nullable = false) // Clave foránea
    private User changeIdUser;

    // tabla relacion con news
    @ManyToMany
    @JoinTable(name = "news_changes", // nombre tabla intermedia
    joinColumns = @JoinColumn(name = "id_changes"), // columna que hace referencia a usuario
    inverseJoinColumns = @JoinColumn(name = "news_code")) // Columna que referencia a project
    private List<News> newsChange; // nombre asignado al mappeo

    // tabla relacion con project
    @ManyToMany
    @JoinTable(name = "project_changes", // nombre tabla intermedia
    joinColumns = @JoinColumn(name = "id_changes"), // columna que hace referencia a usuario
    inverseJoinColumns = @JoinColumn(name = "proj_id")) // Columna que referencia a project
    private List<Project> projectChange; // nombre asignado al mappeo
}
