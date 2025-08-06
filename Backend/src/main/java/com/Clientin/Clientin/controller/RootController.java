package com.Clientin.Clientin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Clientin API");
        response.put("version", "1.0.0");
        response.put("status", "running");
        response.put("documentation", "/swagger-ui.html");
        response.put("api_base", "/api/v1");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("absences", "/api/v1/absences");
        endpoints.put("clients", "/api/v1/clients");
        endpoints.put("employees", "/api/v1/employeeProfiles");
        endpoints.put("audit_logs", "/api/v1/auditLogs");
        endpoints.put("goals", "/api/v1/goals");
        endpoints.put("health", "/actuator/health");
        
        response.put("available_endpoints", endpoints);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(health);
    }
}
