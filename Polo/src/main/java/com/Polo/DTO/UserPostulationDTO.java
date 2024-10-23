package com.Polo.DTO;

import java.util.List;
import lombok.Data;

@Data
public class UserPostulationDTO {
    private Integer id;
    private String userName;
    private String userEmail;
    private List<PostulationDTO> postulations;
}
