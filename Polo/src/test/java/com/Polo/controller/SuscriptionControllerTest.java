package com.Polo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.Polo.model.Suscription;
import com.Polo.service.SuscriptionService;
import com.Polo.repository.SuscriptionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SuscriptionControllerTest {

    @Mock
    private SuscriptionService suscriptionService;

    @Mock
    private SuscriptionRepository suscriptionRepository;

    @InjectMocks
    private SuscriptionController suscriptionController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(suscriptionController).build();
    }

    @Test
    public void testCreateSuscription() throws Exception {
        Suscription suscription = new Suscription();
        suscription.setSubEmail("test@example.com");
        suscription.setSubFullName("Test User");
        suscription.setSubPhone("1234567890");

        when(suscriptionService.createSuscription(any(Suscription.class))).thenReturn(true);

        mockMvc.perform(post("/suscriptor/create")
                .contentType("application/json")
                .content(
                        "{\"subEmail\":\"test@example.com\", \"subFullName\":\"Test User\", \"subPhone\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Suscriptor creado exitosamente"));

        verify(suscriptionService, times(1)).createSuscription(any(Suscription.class));
    }

    @Test
    public void testFindSuscriptionByEmail() throws Exception {
        Suscription suscription = new Suscription();
        suscription.setSubEmail("test@example.com");

        when(suscriptionService.findSuscriptionsByEmail("test@example.com"))
                .thenReturn(Optional.of(suscription));

        mockMvc.perform(get("/suscriptor/search/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subEmail").value("test@example.com"));

        verify(suscriptionService, times(1)).findSuscriptionsByEmail("test@example.com");
    }

    @Test
    public void testDeleteSuscriptorByAdmin() throws Exception {
        Suscription suscription = new Suscription();
        suscription.setSubEmail("test@example.com");

        when(suscriptionService.deleteSuscriptorByMail("test@example.com")).thenReturn(true);

        mockMvc.perform(delete("/suscriptor/deleteSuscriptor")
                .contentType("application/json")
                .content("{\"sessionData\": {\"role\": \"ADMIN\"}, \"subEmail\": \"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Suscriptior deleted successfully"));

        verify(suscriptionService, times(1)).deleteSuscriptorByMail("test@example.com");
    }

    @Test
    public void testUnsubscribe() throws Exception {
        Suscription suscription = new Suscription();
        suscription.setSubEmail("test@example.com");

        when(suscriptionRepository.findBySubEmail("test@example.com")).thenReturn(suscription);

        mockMvc.perform(get("/suscriptor/unsubscribe?email=test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Te has desuscrito exitosamente."));

        verify(suscriptionRepository, times(1)).delete(suscription);
    }
}
