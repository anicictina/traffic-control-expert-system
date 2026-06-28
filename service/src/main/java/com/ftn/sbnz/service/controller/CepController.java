package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.service.CepDemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cep")
public class CepController {

    private final CepDemoService cepDemoService;

    public CepController(CepDemoService cepDemoService) {
        this.cepDemoService = cepDemoService;
    }

    @GetMapping("/demo/agresivna-voznja")
    public ResponseEntity<Map<String, Object>> demoAgresivnaVoznja() {
        return ResponseEntity.ok(cepDemoService.demoAggressiveDriving());
    }

    @GetMapping("/demo/pokusaj-bega")
    public ResponseEntity<Map<String, Object>> demoPokusajBega() {
        return ResponseEntity.ok(cepDemoService.demoPotentialEscape());
    }

    @GetMapping("/demo/nocna-voznja")
    public ResponseEntity<Map<String, Object>> demoNocnaVoznja() {
        return ResponseEntity.ok(cepDemoService.demoHighRiskNight());
    }
}
