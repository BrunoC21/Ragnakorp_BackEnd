package com.Polo.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ChangesDTO {
    private Integer id;
    private String changesDescription;
    private LocalDateTime changesDate;
    private String changesThing;
    private String changesType;
    private UserDTO changeIdUser;
}
