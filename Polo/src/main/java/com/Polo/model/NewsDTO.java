package com.Polo.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NewsDTO {
    private Integer id;
    private String newsTitle;
    private String newsContent;
    private LocalDateTime newsDateTime;
    private String newsCategory;
    private String newsWriter;
    private String primaryImage;
}

