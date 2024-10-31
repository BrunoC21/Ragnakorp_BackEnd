package com.Polo.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ChangesDTO {
    private Integer id;
    private String changesDescription;
    private LocalDateTime changesDate;
    private String changesThing;
    private String changesType;
    private UserDTO changeIdUser;
    // private List<NewsDTO> newsChange;
    // private List<ProjectDTO> projectChange;
}
