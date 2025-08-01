package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.AppSettingsDTO;
import com.Clientin.Clientin.service.AppSettingsService;
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
@RequestMapping("/api/v1/appSettingss")
@RequiredArgsConstructor
@Slf4j
public class AppSettingsController {

    private final AppSettingsService appSettingsService;

    @GetMapping
    public Page<AppSettingsDTO> getAll(Pageable pageable) {
        log.info("Fetching AppSettings with pageable: {}", pageable);
        return appSettingsService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppSettingsDTO> getById(@PathVariable String id) {
        return appSettingsService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AppSettingsDTO> create(@Valid @RequestBody AppSettingsDTO dto) {
        AppSettingsDTO created = appSettingsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppSettingsDTO> update(
            @PathVariable String id,
            @Valid @RequestBody AppSettingsDTO dto) {
        try {
            AppSettingsDTO updated = appSettingsService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppSettingsDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody AppSettingsDTO dto) {
        try {
            AppSettingsDTO updated = appSettingsService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            appSettingsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = appSettingsService.exists(id);
        return ResponseEntity.ok(exists);
    }
}