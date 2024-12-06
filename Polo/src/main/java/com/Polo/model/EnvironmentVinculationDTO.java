package com.Polo.model;

import lombok.Data;

@Data
public class EnvironmentVinculationDTO {
    private Integer id;
    private String activityName;
    private String activityDescription;
    private String activityCategory;
    private UserDTO user;
}

// Relaciones institucionales
// Servicios a la comunidad
// Sostenibilidad
// Medios de comunicacion
// Colaboradores
