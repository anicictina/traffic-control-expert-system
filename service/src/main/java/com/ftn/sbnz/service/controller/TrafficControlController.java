package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.service.TrafficControlService;
import com.ftn.sbnz.service.dto.TrafficControlRequest;
import com.ftn.sbnz.service.dto.TrafficControlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kontrola")
public class TrafficControlController {

    private final TrafficControlService service;

    public TrafficControlController(TrafficControlService service) {
        this.service = service;
    }

    @PostMapping("/proveri")
    public ResponseEntity<TrafficControlResponse> proveri(@RequestBody TrafficControlRequest request) {
        TrafficControlResponse response = service.analyze(request);
        return ResponseEntity.ok(response);
    }
}
