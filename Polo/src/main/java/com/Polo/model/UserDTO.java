package com.Polo.model;

import java.util.List;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String userRut;
    private String userLastName;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userPassword;
    private String userRole;
    private List<ProjectDTO> projects;
    private List<PostulationDTO> postulations;
    private List<NewsDTO> news;
}
