package com.Polo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.Polo.model.StadisticsDTO;
import com.Polo.service.StadisticsService;

import java.util.List;

@RestController
@RequestMapping("/stadistics") // Base path
public class StadisticsController {

    @Autowired
    private StadisticsService stadisticsService;

    @PostMapping("/create")
    public StadisticsDTO createStadistics(@RequestBody StadisticsDTO stadisticsDTO) {
        return stadisticsService.createStadistics(stadisticsDTO);
    }

    @GetMapping("/search")
    public List<StadisticsDTO> getAllStadistics() {
        return stadisticsService.getAllStadistics();
    }
}
