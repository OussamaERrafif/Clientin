package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.SubscriptionDTO;
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

    @GetMapping
    public Page<SubscriptionDTO> getAll(Pageable pageable) {
        log.info("Fetching Subscription with pageable: {}", pageable);
        return subscriptionService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getById(@PathVariable String id) {
        return subscriptionService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubscriptionDTO> create(@Valid @RequestBody SubscriptionDTO dto) {
        SubscriptionDTO created = subscriptionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> update(
            @PathVariable String id,
            @Valid @RequestBody SubscriptionDTO dto) {
        try {
            SubscriptionDTO updated = subscriptionService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody SubscriptionDTO dto) {
        try {
            SubscriptionDTO updated = subscriptionService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            subscriptionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = subscriptionService.exists(id);
        return ResponseEntity.ok(exists);
    }
}