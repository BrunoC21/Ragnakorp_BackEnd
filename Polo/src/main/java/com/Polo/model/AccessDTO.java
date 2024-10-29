package com.Polo.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class AccessDTO {
    private Integer id;
    private LocalDateTime hour;
    private String device;
    private List<Integer> stadisticsIds; // Referencia a la relación Many-to-Many
}

