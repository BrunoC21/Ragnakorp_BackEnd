package com.Polo.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SuscriptionsDTO {
    private Integer id;
    private String subFullName;
    private String subEmail;
    private String subPhone;
    private LocalDateTime subCreationTime;
}

