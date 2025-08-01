package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.PerformanceReviewDTO;
import com.Clientin.Clientin.service.PerformanceReviewService;
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
@RequestMapping("/api/v1/performanceReviews")
@RequiredArgsConstructor
@Slf4j
public class PerformanceReviewController {

    private final PerformanceReviewService performanceReviewService;

    @GetMapping
    public Page<PerformanceReviewDTO> getAll(Pageable pageable) {
        log.info("Fetching PerformanceReview with pageable: {}", pageable);
        return performanceReviewService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceReviewDTO> getById(@PathVariable String id) {
        return performanceReviewService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PerformanceReviewDTO> create(@Valid @RequestBody PerformanceReviewDTO dto) {
        PerformanceReviewDTO created = performanceReviewService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformanceReviewDTO> update(
            @PathVariable String id,
            @Valid @RequestBody PerformanceReviewDTO dto) {
        try {
            PerformanceReviewDTO updated = performanceReviewService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PerformanceReviewDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody PerformanceReviewDTO dto) {
        try {
            PerformanceReviewDTO updated = performanceReviewService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            performanceReviewService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = performanceReviewService.exists(id);
        return ResponseEntity.ok(exists);
    }
}