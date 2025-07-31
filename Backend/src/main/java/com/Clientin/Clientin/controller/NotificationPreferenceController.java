package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.annotation.Loggable;
import com.Clientin.Clientin.dto.NotificationPreferenceDTO;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.service.LogService;
import com.Clientin.Clientin.service.NotificationPreferenceService;
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
@RequestMapping("/api/v1/notificationPreferences")
@RequiredArgsConstructor
@Slf4j
public class NotificationPreferenceController {

    private final NotificationPreferenceService notificationPreferenceService;
    private final LogService logService;

    @GetMapping
    @Loggable(action = Log.LogAction.READ, entity = "NotificationPreference", message = "Fetching all notification preferences")
    public Page<NotificationPreferenceDTO> getAll(Pageable pageable) {
        log.info("Fetching NotificationPreference with pageable: {}", pageable);
        return notificationPreferenceService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "NotificationPreference", message = "Fetching notification preference by ID", logParams = true)
    public ResponseEntity<NotificationPreferenceDTO> getById(@PathVariable String id) {
        logService.logRead("NotificationPreference", id, "system", "Attempting to fetch notification preference by ID");
        return notificationPreferenceService.findById(id)
                .map(dto -> {
                    logService.logRead("NotificationPreference", id, "system", "Successfully fetched notification preference");
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Loggable(action = Log.LogAction.CREATE, entity = "NotificationPreference", message = "Creating new notification preference", logParams = true, logReturn = true)
    public ResponseEntity<NotificationPreferenceDTO> create(@Valid @RequestBody NotificationPreferenceDTO dto) {
        logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "NotificationPreference", null, "system", 
                           "Attempting to create new notification preference");
        NotificationPreferenceDTO created = notificationPreferenceService.create(dto);
        logService.logCreate("NotificationPreference", created.getUserId(), "system", 
                           "Notification preference created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "NotificationPreference", message = "Updating notification preference", logParams = true)
    public ResponseEntity<NotificationPreferenceDTO> update(
            @PathVariable String id,
            @Valid @RequestBody NotificationPreferenceDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "NotificationPreference", id, "system", "Attempting to update notification preference");
            NotificationPreferenceDTO updated = notificationPreferenceService.update(id, dto);
            logService.logUpdate("NotificationPreference", id, "system", "Notification preference updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("NotificationPreference", id, "system", "Failed to update notification preference", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "NotificationPreference", message = "Partially updating notification preference", logParams = true)
    public ResponseEntity<NotificationPreferenceDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody NotificationPreferenceDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "NotificationPreference", id, "system", "Attempting partial update of notification preference");
            NotificationPreferenceDTO updated = notificationPreferenceService.partialUpdate(id, dto);
            logService.logUpdate("NotificationPreference", id, "system", "Notification preference partially updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("NotificationPreference", id, "system", "Failed to partially update notification preference", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Loggable(action = Log.LogAction.DELETE, entity = "NotificationPreference", message = "Deleting notification preference", logParams = true)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, "NotificationPreference", id, "system", "Attempting to delete notification preference");
            notificationPreferenceService.delete(id);
            logService.logDelete("NotificationPreference", id, "system", "Notification preference deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logService.logError("NotificationPreference", id, "system", "Failed to delete notification preference", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "NotificationPreference", message = "Checking notification preference existence", logParams = true, logReturn = true)
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = notificationPreferenceService.exists(id);
        logService.logRead("NotificationPreference", id, "system", "Checked notification preference existence: " + exists);
        return ResponseEntity.ok(exists);
    }
}