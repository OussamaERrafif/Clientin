package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.NotificationPreferenceDTO;
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

    @GetMapping
    public Page<NotificationPreferenceDTO> getAll(Pageable pageable) {
        log.info("Fetching NotificationPreference with pageable: {}", pageable);
        return notificationPreferenceService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationPreferenceDTO> getById(@PathVariable String id) {
        return notificationPreferenceService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NotificationPreferenceDTO> create(@Valid @RequestBody NotificationPreferenceDTO dto) {
        NotificationPreferenceDTO created = notificationPreferenceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationPreferenceDTO> update(
            @PathVariable String id,
            @Valid @RequestBody NotificationPreferenceDTO dto) {
        try {
            NotificationPreferenceDTO updated = notificationPreferenceService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NotificationPreferenceDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody NotificationPreferenceDTO dto) {
        try {
            NotificationPreferenceDTO updated = notificationPreferenceService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            notificationPreferenceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = notificationPreferenceService.exists(id);
        return ResponseEntity.ok(exists);
    }
}