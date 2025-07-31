package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.InsightDTO;
import com.Clientin.Clientin.service.InsightService;
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

    @GetMapping
    public Page<InsightDTO> getAll(Pageable pageable) {
        log.info("Fetching Insight with pageable: {}", pageable);
        return insightService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsightDTO> getById(@PathVariable String id) {
        return insightService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InsightDTO> create(@Valid @RequestBody InsightDTO dto) {
        InsightDTO created = insightService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsightDTO> update(
            @PathVariable String id,
            @Valid @RequestBody InsightDTO dto) {
        try {
            InsightDTO updated = insightService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InsightDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody InsightDTO dto) {
        try {
            InsightDTO updated = insightService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            insightService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = insightService.exists(id);
        return ResponseEntity.ok(exists);
    }
}