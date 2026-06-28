package com.ftn.sbnz.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/whoami")
    public ResponseEntity<Map<String, String>> whoami(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        String role = authentication.getAuthorities().stream()
            .map(a -> a.getAuthority())
            .findFirst()
            .orElse("UNKNOWN");

        Map<String, String> result = new LinkedHashMap<>();
        result.put("username", authentication.getName());
        result.put("role", role);
        return ResponseEntity.ok(result);
    }
}
