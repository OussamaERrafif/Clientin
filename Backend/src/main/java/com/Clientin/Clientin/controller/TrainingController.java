package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.TrainingDTO;
import com.Clientin.Clientin.service.TrainingService;
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
@RequestMapping("/api/v1/trainings")
@RequiredArgsConstructor
@Slf4j
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping
    public Page<TrainingDTO> getAll(Pageable pageable) {
        log.info("Fetching Training with pageable: {}", pageable);
        return trainingService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDTO> getById(@PathVariable String id) {
        return trainingService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TrainingDTO> create(@Valid @RequestBody TrainingDTO dto) {
        TrainingDTO created = trainingService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDTO> update(
            @PathVariable String id,
            @Valid @RequestBody TrainingDTO dto) {
        try {
            TrainingDTO updated = trainingService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TrainingDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody TrainingDTO dto) {
        try {
            TrainingDTO updated = trainingService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            trainingService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = trainingService.exists(id);
        return ResponseEntity.ok(exists);
    }
}