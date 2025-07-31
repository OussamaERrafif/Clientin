package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.annotation.Loggable;
import com.Clientin.Clientin.dto.FeedbackDTO;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.service.FeedbackService;
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
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final LogService logService;

    @GetMapping
    @Loggable(action = Log.LogAction.READ, entity = "Feedback", message = "Fetching all feedbacks")
    public Page<FeedbackDTO> getAll(Pageable pageable) {
        log.info("Fetching Feedback with pageable: {}", pageable);
        return feedbackService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "Feedback", message = "Fetching feedback by ID", logParams = true)
    public ResponseEntity<FeedbackDTO> getById(@PathVariable String id) {
        logService.logRead("Feedback", id, "system", "Attempting to fetch feedback by ID");
        return feedbackService.findById(id)
                .map(dto -> {
                    logService.logRead("Feedback", id, "system", "Successfully fetched feedback");
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Loggable(action = Log.LogAction.CREATE, entity = "Feedback", message = "Creating new feedback", logParams = true, logReturn = true)
    public ResponseEntity<FeedbackDTO> create(@Valid @RequestBody FeedbackDTO dto) {
        logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "Feedback", null, "system", 
                           "Attempting to create new feedback");
        FeedbackDTO created = feedbackService.create(dto);
        logService.logCreate("Feedback", created.getId(), "system", 
                           "Feedback created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "Feedback", message = "Updating feedback", logParams = true)
    public ResponseEntity<FeedbackDTO> update(
            @PathVariable String id,
            @Valid @RequestBody FeedbackDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Feedback", id, "system", "Attempting to update feedback");
            FeedbackDTO updated = feedbackService.update(id, dto);
            logService.logUpdate("Feedback", id, "system", "Feedback updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("Feedback", id, "system", "Failed to update feedback", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "Feedback", message = "Partially updating feedback", logParams = true)
    public ResponseEntity<FeedbackDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody FeedbackDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Feedback", id, "system", "Attempting partial update of feedback");
            FeedbackDTO updated = feedbackService.partialUpdate(id, dto);
            logService.logUpdate("Feedback", id, "system", "Feedback partially updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("Feedback", id, "system", "Failed to partially update feedback", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Loggable(action = Log.LogAction.DELETE, entity = "Feedback", message = "Deleting feedback", logParams = true)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, "Feedback", id, "system", "Attempting to delete feedback");
            feedbackService.delete(id);
            logService.logDelete("Feedback", id, "system", "Feedback deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logService.logError("Feedback", id, "system", "Failed to delete feedback", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "Feedback", message = "Checking feedback existence", logParams = true, logReturn = true)
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = feedbackService.exists(id);
        logService.logRead("Feedback", id, "system", "Checked feedback existence: " + exists);
        return ResponseEntity.ok(exists);
    }
}