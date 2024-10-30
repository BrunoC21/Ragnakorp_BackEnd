package com.Polo.model;

import java.util.List;
import lombok.Data;

@Data
public class PostulationDTO {
    private Integer id;
    private String postulationName;
    private String postulationDescription;
    private String postulationProject;
    // private List<UserDTO> user;
    // private List<ProjectDTO> project;
}
