package com.Polo.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class NewsDTO {
    private Integer id;
    private String newsTitle;
    private String newsContent;
    private LocalDateTime newsDateTime;
    private String secondaryImage;
    private String newsCategory;
    private String newsWriter;
    private String newsPicture;
    private String primaryImage;
    private List<UserDTO> user;
    private List<ChangesDTO> changes;
}
