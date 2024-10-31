package com.Polo.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StadisticsDTO {
    private Integer id;
    private Integer totalAccess;
    private LocalDateTime accessDay;
}

