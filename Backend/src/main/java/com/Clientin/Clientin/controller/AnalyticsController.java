package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.AnalyticsDTO;
import com.Clientin.Clientin.service.AnalyticsService;
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
@RequestMapping("/api/v1/analyticss")
@RequiredArgsConstructor
@Slf4j
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    public Page<AnalyticsDTO> getAll(Pageable pageable) {
        log.info("Fetching Analytics with pageable: {}", pageable);
        return analyticsService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyticsDTO> getById(@PathVariable String id) {
        return analyticsService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AnalyticsDTO> create(@Valid @RequestBody AnalyticsDTO dto) {
        AnalyticsDTO created = analyticsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalyticsDTO> update(
            @PathVariable String id,
            @Valid @RequestBody AnalyticsDTO dto) {
        try {
            AnalyticsDTO updated = analyticsService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AnalyticsDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody AnalyticsDTO dto) {
        try {
            AnalyticsDTO updated = analyticsService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            analyticsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = analyticsService.exists(id);
        return ResponseEntity.ok(exists);
    }
}