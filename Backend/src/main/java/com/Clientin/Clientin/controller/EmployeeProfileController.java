package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.annotation.Loggable;
import com.Clientin.Clientin.dto.EmployeeProfileDTO;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.service.EmployeeProfileService;
import com.Clientin.Clientin.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employeeProfiles")
@RequiredArgsConstructor
@Slf4j
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;
    private final LogService logService;

    @GetMapping
    @Loggable(action = Log.LogAction.READ, entity = "EmployeeProfile", message = "Fetching all employee profiles")
    public Page<EmployeeProfileDTO> getAll(Pageable pageable) {
        log.info("Fetching EmployeeProfile with pageable: {}", pageable);
        return employeeProfileService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "EmployeeProfile", message = "Fetching employee profile by ID", logParams = true)
    public ResponseEntity<EmployeeProfileDTO> getById(@PathVariable String id) {
        logService.logRead("EmployeeProfile", id, "system", "Attempting to fetch employee profile by ID");
        return employeeProfileService.findById(id)
                .map(dto -> {
                    logService.logRead("EmployeeProfile", id, "system", "Successfully fetched employee profile");
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Loggable(action = Log.LogAction.CREATE, entity = "EmployeeProfile", message = "Creating new employee profile", logParams = true, logReturn = true)
    public ResponseEntity<EmployeeProfileDTO> create(@Valid @RequestBody EmployeeProfileDTO dto) {
        logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "EmployeeProfile", null, "system", 
                           "Attempting to create new employee profile");
        EmployeeProfileDTO created = employeeProfileService.create(dto);
        logService.logCreate("EmployeeProfile", created.getUserId(), "system", 
                           "Employee profile created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "EmployeeProfile", message = "Updating employee profile", logParams = true)
    public ResponseEntity<EmployeeProfileDTO> update(
            @PathVariable String id,
            @Valid @RequestBody EmployeeProfileDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "EmployeeProfile", id, "system", "Attempting to update employee profile");
            EmployeeProfileDTO updated = employeeProfileService.update(id, dto);
            logService.logUpdate("EmployeeProfile", id, "system", "Employee profile updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("EmployeeProfile", id, "system", "Failed to update employee profile", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "EmployeeProfile", message = "Partially updating employee profile", logParams = true)
    public ResponseEntity<EmployeeProfileDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody EmployeeProfileDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "EmployeeProfile", id, "system", "Attempting partial update of employee profile");
            EmployeeProfileDTO updated = employeeProfileService.partialUpdate(id, dto);
            logService.logUpdate("EmployeeProfile", id, "system", "Employee profile partially updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("EmployeeProfile", id, "system", "Failed to partially update employee profile", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Loggable(action = Log.LogAction.DELETE, entity = "EmployeeProfile", message = "Deleting employee profile", logParams = true)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, "EmployeeProfile", id, "system", "Attempting to delete employee profile");
            employeeProfileService.delete(id);
            logService.logDelete("EmployeeProfile", id, "system", "Employee profile deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logService.logError("EmployeeProfile", id, "system", "Failed to delete employee profile", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "EmployeeProfile", message = "Checking employee profile existence", logParams = true, logReturn = true)
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = employeeProfileService.exists(id);
        logService.logRead("EmployeeProfile", id, "system", "Checked employee profile existence: " + exists);
        return ResponseEntity.ok(exists);
    }
}