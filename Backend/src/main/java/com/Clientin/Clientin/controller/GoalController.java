package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.annotation.Loggable;
import com.Clientin.Clientin.dto.GoalDTO;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.service.GoalService;
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
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
@Slf4j
public class GoalController {

    private final GoalService goalService;
    private final LogService logService;

    @GetMapping
    @Loggable(action = Log.LogAction.READ, entity = "Goal", message = "Fetching all goals")
    public Page<GoalDTO> getAll(Pageable pageable) {
        log.info("Fetching Goal with pageable: {}", pageable);
        return goalService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "Goal", message = "Fetching goal by ID", logParams = true)
    public ResponseEntity<GoalDTO> getById(@PathVariable String id) {
        logService.logRead("Goal", id, "system", "Attempting to fetch goal by ID");
        return goalService.findById(id)
                .map(dto -> {
                    logService.logRead("Goal", id, "system", "Successfully fetched goal");
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Loggable(action = Log.LogAction.CREATE, entity = "Goal", message = "Creating new goal", logParams = true, logReturn = true)
    public ResponseEntity<GoalDTO> create(@Valid @RequestBody GoalDTO dto) {
        logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "Goal", null, "system", 
                           "Attempting to create new goal");
        GoalDTO created = goalService.create(dto);
        logService.logCreate("Goal", created.getId(), "system", 
                           "Goal created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "Goal", message = "Updating goal", logParams = true)
    public ResponseEntity<GoalDTO> update(
            @PathVariable String id,
            @Valid @RequestBody GoalDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Goal", id, "system", "Attempting to update goal");
            GoalDTO updated = goalService.update(id, dto);
            logService.logUpdate("Goal", id, "system", "Goal updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("Goal", id, "system", "Failed to update goal", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "Goal", message = "Partially updating goal", logParams = true)
    public ResponseEntity<GoalDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody GoalDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Goal", id, "system", "Attempting partial update of goal");
            GoalDTO updated = goalService.partialUpdate(id, dto);
            logService.logUpdate("Goal", id, "system", "Goal partially updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("Goal", id, "system", "Failed to partially update goal", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Loggable(action = Log.LogAction.DELETE, entity = "Goal", message = "Deleting goal", logParams = true)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, "Goal", id, "system", "Attempting to delete goal");
            goalService.delete(id);
            logService.logDelete("Goal", id, "system", "Goal deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logService.logError("Goal", id, "system", "Failed to delete goal", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "Goal", message = "Checking goal existence", logParams = true, logReturn = true)
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = goalService.exists(id);
        logService.logRead("Goal", id, "system", "Checked goal existence: " + exists);
        return ResponseEntity.ok(exists);
    }
}