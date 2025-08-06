package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.RewardDTO;
import com.Clientin.Clientin.service.RewardService;
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
@RequestMapping("/api/v1/rewards")
@RequiredArgsConstructor
@Slf4j
public class RewardController {

    private final RewardService rewardService;

    @GetMapping
    public Page<RewardDTO> getAll(Pageable pageable) {
        log.info("Fetching Reward with pageable: {}", pageable);
        return rewardService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RewardDTO> getById(@PathVariable String id) {
        return rewardService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RewardDTO> create(@Valid @RequestBody RewardDTO dto) {
        RewardDTO created = rewardService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RewardDTO> update(
            @PathVariable String id,
            @Valid @RequestBody RewardDTO dto) {
        try {
            RewardDTO updated = rewardService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RewardDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody RewardDTO dto) {
        try {
            RewardDTO updated = rewardService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            rewardService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = rewardService.exists(id);
        return ResponseEntity.ok(exists);
    }
}