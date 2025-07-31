package com.Clientin.Clientin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health Check", description = "Health check endpoints for monitoring application status")
public class HealthController {

    @GetMapping
    @Operation(
            summary = "Check application health",
            description = "Returns the health status of the Clientin application"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Application is healthy",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)
                    )
            )
    })
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "Clientin");
        response.put("version", "1.0.0");
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detailed")
    @Operation(
            summary = "Get detailed health information",
            description = "Returns detailed health information including system metrics"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Detailed health information retrieved successfully"
            )
    })
    public ResponseEntity<Map<String, Object>> detailedHealthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "Clientin");
        response.put("version", "1.0.0");
        response.put("timestamp", System.currentTimeMillis());
        
        // Add system information
        Map<String, Object> system = new HashMap<>();
        system.put("javaVersion", System.getProperty("java.version"));
        system.put("osName", System.getProperty("os.name"));
        system.put("osVersion", System.getProperty("os.version"));
        system.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        system.put("maxMemory", Runtime.getRuntime().maxMemory());
        system.put("totalMemory", Runtime.getRuntime().totalMemory());
        system.put("freeMemory", Runtime.getRuntime().freeMemory());
        
        response.put("system", system);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ping")
    @Operation(
            summary = "Simple ping endpoint",
            description = "Simple endpoint to check if the service is responding"
    )
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
