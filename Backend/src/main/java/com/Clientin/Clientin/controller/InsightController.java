package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.annotation.Loggable;
import com.Clientin.Clientin.dto.InsightDTO;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.service.InsightService;
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
@RequestMapping("/api/v1/insights")
@RequiredArgsConstructor
@Slf4j
public class InsightController {

    private final InsightService insightService;
    private final LogService logService;

    @GetMapping
    @Loggable(action = Log.LogAction.READ, entity = "Insight", message = "Fetching all insights")
    public Page<InsightDTO> getAll(Pageable pageable) {
        log.info("Fetching Insight with pageable: {}", pageable);
        return insightService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "Insight", message = "Fetching insight by ID", logParams = true)
    public ResponseEntity<InsightDTO> getById(@PathVariable String id) {
        logService.logRead("Insight", id, "system", "Attempting to fetch insight by ID");
        return insightService.findById(id)
                .map(dto -> {
                    logService.logRead("Insight", id, "system", "Successfully fetched insight");
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Loggable(action = Log.LogAction.CREATE, entity = "Insight", message = "Creating new insight", logParams = true, logReturn = true)
    public ResponseEntity<InsightDTO> create(@Valid @RequestBody InsightDTO dto) {
        logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "Insight", null, "system", 
                           "Attempting to create new insight");
        InsightDTO created = insightService.create(dto);
        logService.logCreate("Insight", created.getId(), "system", 
                           "Insight created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "Insight", message = "Updating insight", logParams = true)
    public ResponseEntity<InsightDTO> update(
            @PathVariable String id,
            @Valid @RequestBody InsightDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Insight", id, "system", "Attempting to update insight");
            InsightDTO updated = insightService.update(id, dto);
            logService.logUpdate("Insight", id, "system", "Insight updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("Insight", id, "system", "Failed to update insight", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "Insight", message = "Partially updating insight", logParams = true)
    public ResponseEntity<InsightDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody InsightDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Insight", id, "system", "Attempting partial update of insight");
            InsightDTO updated = insightService.partialUpdate(id, dto);
            logService.logUpdate("Insight", id, "system", "Insight partially updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("Insight", id, "system", "Failed to partially update insight", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Loggable(action = Log.LogAction.DELETE, entity = "Insight", message = "Deleting insight", logParams = true)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, "Insight", id, "system", "Attempting to delete insight");
            insightService.delete(id);
            logService.logDelete("Insight", id, "system", "Insight deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logService.logError("Insight", id, "system", "Failed to delete insight", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "Insight", message = "Checking insight existence", logParams = true, logReturn = true)
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = insightService.exists(id);
        logService.logRead("Insight", id, "system", "Checked insight existence: " + exists);
        return ResponseEntity.ok(exists);
    }
}