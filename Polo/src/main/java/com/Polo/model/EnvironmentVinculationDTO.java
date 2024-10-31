package com.Polo.model;

import lombok.Data;

@Data
public class EnvironmentVinculationDTO {
    private Integer id;
    private String activityName;
    private String activityDescription;
    private UserDTO user;
}
