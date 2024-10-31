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
public class Polocenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "centername", nullable = false)
    private String centerName;

    @Column(name = "centerdescription", nullable = false, columnDefinition = "TEXT")
    private String centerDescription;

    @Column(name = "centerlocation", nullable = false)
    private String centerLocation;

    @Column(name = "centerdirection", nullable = false, columnDefinition = "TEXT")
    private String centerDirection;

    @Column(name = "centercontact", nullable = false)
    private String centerContact;
}
