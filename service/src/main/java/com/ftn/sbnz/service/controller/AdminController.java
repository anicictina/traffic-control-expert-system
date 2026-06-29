package com.ftn.sbnz.service.controller;

import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final KieBase kieBase;

    public AdminController(KieBase kieBase) {
        this.kieBase = kieBase;
    }

    @GetMapping("/pravila")
    public ResponseEntity<Map<String, List<String>>> listaPravila() {
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (KiePackage pkg : kieBase.getKiePackages()) {
            List<String> names = pkg.getRules().stream()
                .map(Rule::getName)
                .sorted()
                .collect(Collectors.toList());
            if (!names.isEmpty()) {
                result.put(pkg.getName(), names);
            }
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/drl/{naziv}")
    public ResponseEntity<String> drlFajl(@PathVariable String naziv) {
        String putanja = switch (naziv) {
            case "forward"  -> "/com/ftn/sbnz/kjar/rules/traffic-rules.drl";
            case "backward" -> "/com/ftn/sbnz/kjar/rules/traffic-backward-rules.drl";
            case "cep"      -> "/com/ftn/sbnz/kjar/rules/traffic-cep-rules.drl";
            default -> null;
        };
        if (putanja == null) return ResponseEntity.notFound().build();
        try (InputStream is = getClass().getResourceAsStream(putanja)) {
            if (is == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(new String(is.readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Greška pri čitanju fajla");
        }
    }
}
