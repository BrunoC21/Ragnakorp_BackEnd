package com.Polo.controller;

import com.Polo.model.EnvironmentVinculationDTO;
import com.Polo.service.EnvironmentVinculationService;
import com.Polo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EnvironmentVinculationTest {

    @InjectMocks
    private EnvironmentVinculationController environmentVinculationController;

    @Mock
    private EnvironmentVinculationService environmentVinculationService;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(environmentVinculationController).build();
    }

    @Test
    public void testFindAllActivities() throws Exception {
        EnvironmentVinculationDTO dto = new EnvironmentVinculationDTO();
        dto.setActivityName("Activity 1");
        dto.setActivityCategory("Environment");
        when(environmentVinculationService.findAllActivities()).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/environmentVinculation/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].activityName").value("Activity 1"));
    }

    @Test
    public void testFindByActivityId() throws Exception {
        int activityId = 1;
        EnvironmentVinculationDTO dto = new EnvironmentVinculationDTO();
        dto.setActivityName("Activity 1");
        dto.setActivityCategory("Environment");
        Optional<EnvironmentVinculationDTO> optionalDTO = Optional.of(dto);
        when(environmentVinculationService.findByActivityId(activityId)).thenReturn(optionalDTO);

        mockMvc.perform(get("/environmentVinculation/search/{id}", activityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityName").value("Activity 1"));
    }

    @Test
    public void testFindByActivityName() throws Exception {
        String activityName = "Test Activity";
        EnvironmentVinculationDTO dto = new EnvironmentVinculationDTO();
        dto.setActivityName(activityName);
        dto.setActivityCategory("Environment");
        Optional<EnvironmentVinculationDTO> optionalDTO = Optional.of(dto);
        when(environmentVinculationService.findByActivityName(activityName)).thenReturn(optionalDTO);

        mockMvc.perform(get("/environmentVinculation/search/name/{activityName}", activityName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityName").value(activityName));
    }

    @Test
    public void testDeleteActivity() throws Exception {
        int activityId = 1;
        when(environmentVinculationService.deleteActivity(activityId)).thenReturn(true);

        mockMvc.perform(delete("/environmentVinculation/delete/{id}", activityId))
                .andExpect(status().isOk())
                .andExpect(content().string("Actividad eliminada existosamente"));
    }
}
