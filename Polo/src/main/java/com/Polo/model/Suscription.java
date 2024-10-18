package com.Polo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Suscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_code", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "sub_fullname", nullable = false)
    private String subFullName;

    @Column(name = "sub_email", nullable = false)
    private String subEmail;

    @Column(name = "sub_phone", nullable = false)
    private String subPhone;

    @CreationTimestamp
    @Column(name = "sub_datesuscription", nullable = false, updatable = false)
    private LocalDateTime subCreationTime;
    
}
