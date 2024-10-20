package com.Polo.model;

import java.util.List;
import jakarta.persistence.*;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "user_rut", nullable = false)
    private String userRut;

    @Column(name = "user_lastname", nullable = false)
    private String userLastName;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_phone", nullable = false)
    private String userPhone;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    // tabla relacion con users
    @ManyToMany
    @JoinTable(name = "user_project", // nombre tabla intermedia
            joinColumns = @JoinColumn(name = "user_id"), // columna que hace referencia a usuario
            inverseJoinColumns = @JoinColumn(name = "proj_id")) // Columna que referencia a project
    private List<Project> projects; // nombre asignado al mappeo

    // tabla relacion con postulation
    @ManyToMany
    @JoinTable(name = "user_postulation", // nombre tabla intermedia
            joinColumns = @JoinColumn(name = "user_id"), // columna que hace referencia a usuario
            inverseJoinColumns = @JoinColumn(name = "id_postulation")) // Columna que referencia a project
    private List<Postulation> postulation; // nombre asignado al mappeo

    // tabla relacion con news
    @ManyToMany
    @JoinTable(name = "user_news", // nombre tabla intermedia
            joinColumns = @JoinColumn(name = "user_id"), // columna que hace referencia a usuario
            inverseJoinColumns = @JoinColumn(name = "news_code")) // Columna que referencia a project
    private List<News> news; // nombre asignado al mappeo

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<EnviromentVinculation> enviromentVinculation;
}
