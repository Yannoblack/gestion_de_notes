package com.projetfulstack.studentgrademanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Health Check Controller
 * Provides health check endpoints for monitoring
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
@RestController
@RequestMapping("/public")
@Tag(name = "Health Check", description = "Health check and system status endpoints")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Health Check", description = "Returns the current system status and timestamp")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "Student Grade Management System");
        response.put("version", "1.0.0");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    @Operation(summary = "System Info", description = "Returns basic system information")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Student Grade Management System");
        response.put("version", "1.0.0");
        response.put("description", "RESTful API for managing student grades, subjects, and academic records");
        response.put("author", "ProjetFullStack Team");
        response.put("java.version", System.getProperty("java.version"));
        response.put("spring.boot.version", org.springframework.boot.SpringBootVersion.getVersion());
        
        return ResponseEntity.ok(response);
    }
}
