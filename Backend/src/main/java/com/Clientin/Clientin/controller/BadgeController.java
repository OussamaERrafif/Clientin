package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.BadgeDTO;
import com.Clientin.Clientin.service.BadgeService;
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
@RequestMapping("/api/v1/badges")
@RequiredArgsConstructor
@Slf4j
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping
    public Page<BadgeDTO> getAll(Pageable pageable) {
        log.info("Fetching Badge with pageable: {}", pageable);
        return badgeService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BadgeDTO> getById(@PathVariable String id) {
        return badgeService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BadgeDTO> create(@Valid @RequestBody BadgeDTO dto) {
        BadgeDTO created = badgeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BadgeDTO> update(
            @PathVariable String id,
            @Valid @RequestBody BadgeDTO dto) {
        try {
            BadgeDTO updated = badgeService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BadgeDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody BadgeDTO dto) {
        try {
            BadgeDTO updated = badgeService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            badgeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = badgeService.exists(id);
        return ResponseEntity.ok(exists);
    }
}