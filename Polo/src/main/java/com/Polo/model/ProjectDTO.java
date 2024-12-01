package com.Polo.model;

import lombok.Data;

@Data
public class ProjectDTO {

    private Integer id;
    private String projName;
    private String projDescription;
    private String projLong;
    private String projStartDate;
    private String projRequirementsPostulation;
    private String projLat;
    private String projBudget;
    private String projCategory;
    private String projAddress;
    private String projPicture;
}
