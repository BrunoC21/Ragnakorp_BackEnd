package com.Polo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Stadistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stadistic", nullable = false)
    private Integer id;

    @Column(name = "total_acces", nullable = false)
    private Integer totalAccess;

    @CreationTimestamp
    @Column(name = "acces_day", nullable = false, updatable = false)
    private LocalDateTime accessDay;

}
