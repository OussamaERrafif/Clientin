package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.NFCSessionDTO;
import com.Clientin.Clientin.service.NFCSessionService;
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
@RequestMapping("/api/v1/nFCSessions")
@RequiredArgsConstructor
@Slf4j
public class NFCSessionController {

    private final NFCSessionService nFCSessionService;

    @GetMapping
    public Page<NFCSessionDTO> getAll(Pageable pageable) {
        log.info("Fetching NFCSession with pageable: {}", pageable);
        return nFCSessionService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NFCSessionDTO> getById(@PathVariable String id) {
        return nFCSessionService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NFCSessionDTO> create(@Valid @RequestBody NFCSessionDTO dto) {
        NFCSessionDTO created = nFCSessionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NFCSessionDTO> update(
            @PathVariable String id,
            @Valid @RequestBody NFCSessionDTO dto) {
        try {
            NFCSessionDTO updated = nFCSessionService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NFCSessionDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody NFCSessionDTO dto) {
        try {
            NFCSessionDTO updated = nFCSessionService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            nFCSessionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = nFCSessionService.exists(id);
        return ResponseEntity.ok(exists);
    }
}