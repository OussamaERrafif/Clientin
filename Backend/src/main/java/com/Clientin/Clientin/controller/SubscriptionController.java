package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.annotation.Loggable;
import com.Clientin.Clientin.dto.SubscriptionDTO;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.service.LogService;
import com.Clientin.Clientin.service.SubscriptionService;
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
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final LogService logService;

    @GetMapping
    @Loggable(action = Log.LogAction.READ, entity = "Subscription", message = "Fetching all subscriptions")
    public Page<SubscriptionDTO> getAll(Pageable pageable) {
        log.info("Fetching Subscription with pageable: {}", pageable);
        return subscriptionService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "Subscription", message = "Fetching subscription by ID", logParams = true)
    public ResponseEntity<SubscriptionDTO> getById(@PathVariable String id) {
        logService.logRead("Subscription", id, "system", "Attempting to fetch subscription by ID");
        return subscriptionService.findById(id)
                .map(dto -> {
                    logService.logRead("Subscription", id, "system", "Successfully fetched subscription");
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Loggable(action = Log.LogAction.CREATE, entity = "Subscription", message = "Creating new subscription", logParams = true, logReturn = true)
    public ResponseEntity<SubscriptionDTO> create(@Valid @RequestBody SubscriptionDTO dto) {
        logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "Subscription", null, "system", 
                           "Attempting to create new subscription");
        SubscriptionDTO created = subscriptionService.create(dto);
        logService.logCreate("Subscription", created.getId(), "system", 
                           "Subscription created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "Subscription", message = "Updating subscription", logParams = true)
    public ResponseEntity<SubscriptionDTO> update(
            @PathVariable String id,
            @Valid @RequestBody SubscriptionDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Subscription", id, "system", "Attempting to update subscription");
            SubscriptionDTO updated = subscriptionService.update(id, dto);
            logService.logUpdate("Subscription", id, "system", "Subscription updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("Subscription", id, "system", "Failed to update subscription", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "Subscription", message = "Partially updating subscription", logParams = true)
    public ResponseEntity<SubscriptionDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody SubscriptionDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "Subscription", id, "system", "Attempting partial update of subscription");
            SubscriptionDTO updated = subscriptionService.partialUpdate(id, dto);
            logService.logUpdate("Subscription", id, "system", "Subscription partially updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logService.logError("Subscription", id, "system", "Failed to partially update subscription", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Loggable(action = Log.LogAction.DELETE, entity = "Subscription", message = "Deleting subscription", logParams = true)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, "Subscription", id, "system", "Attempting to delete subscription");
            subscriptionService.delete(id);
            logService.logDelete("Subscription", id, "system", "Subscription deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logService.logError("Subscription", id, "system", "Failed to delete subscription", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "Subscription", message = "Checking subscription existence", logParams = true, logReturn = true)
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = subscriptionService.exists(id);
        logService.logRead("Subscription", id, "system", "Checked subscription existence: " + exists);
        return ResponseEntity.ok(exists);
    }
}