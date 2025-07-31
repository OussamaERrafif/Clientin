package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.GoalDTO;
import com.Clientin.Clientin.service.GoalService;
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
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
@Slf4j
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public Page<GoalDTO> getAll(Pageable pageable) {
        log.info("Fetching Goal with pageable: {}", pageable);
        return goalService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDTO> getById(@PathVariable String id) {
        return goalService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GoalDTO> create(@Valid @RequestBody GoalDTO dto) {
        GoalDTO created = goalService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalDTO> update(
            @PathVariable String id,
            @Valid @RequestBody GoalDTO dto) {
        try {
            GoalDTO updated = goalService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GoalDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody GoalDTO dto) {
        try {
            GoalDTO updated = goalService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            goalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = goalService.exists(id);
        return ResponseEntity.ok(exists);
    }
}