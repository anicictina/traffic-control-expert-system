package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.service.TemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sabloni")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("/svi")
    public ResponseEntity<Map<String, String>> sviSabloni() {
        return ResponseEntity.ok(templateService.sviGenerisaniSabloni());
    }

    @GetMapping("/istek-dokumenta")
    public ResponseEntity<String> istekaDozvole() {
        return ResponseEntity.ok(templateService.generisiPravilaIstekaDozvole());
    }

    @GetMapping("/pocetnici")
    public ResponseEntity<String> pocetniciVozaci() {
        return ResponseEntity.ok(templateService.generisiPravilaPocetnikaVozaca());
    }

    @GetMapping("/profesionalni")
    public ResponseEntity<String> profesionalniVozaci() {
        return ResponseEntity.ok(templateService.generisiPravilaProfesionalnihVozaca());
    }

    @GetMapping("/kontekstualni-rizik")
    public ResponseEntity<String> kontekstualniRizik() {
        return ResponseEntity.ok(templateService.generisiPravilaKontekstRizika());
    }

    @GetMapping("/cep-alarmi")
    public ResponseEntity<String> cepAlarmi() {
        return ResponseEntity.ok(templateService.generisiPravilaCepAlarmi());
    }
}
