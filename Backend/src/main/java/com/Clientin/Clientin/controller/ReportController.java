package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.ReportDTO;
import com.Clientin.Clientin.service.ReportService;
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
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public Page<ReportDTO> getAll(Pageable pageable) {
        log.info("Fetching Report with pageable: {}", pageable);
        return reportService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDTO> getById(@PathVariable String id) {
        return reportService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReportDTO> create(@Valid @RequestBody ReportDTO dto) {
        ReportDTO created = reportService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportDTO> update(
            @PathVariable String id,
            @Valid @RequestBody ReportDTO dto) {
        try {
            ReportDTO updated = reportService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReportDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody ReportDTO dto) {
        try {
            ReportDTO updated = reportService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            reportService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = reportService.exists(id);
        return ResponseEntity.ok(exists);
    }
}