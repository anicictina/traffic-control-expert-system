package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistike")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/istorija")
    public ResponseEntity<List<Map<String, Object>>> getHistory() {
        return ResponseEntity.ok(statisticsService.getHistory());
    }
}
