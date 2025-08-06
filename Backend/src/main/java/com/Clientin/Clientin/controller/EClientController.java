package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.EClientDTO;
import com.Clientin.Clientin.service.EClientService;
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
@RequestMapping("/api/v1/eClients")
@RequiredArgsConstructor
@Slf4j
public class EClientController {

    private final EClientService eClientService;

    @GetMapping
    public Page<EClientDTO> getAll(Pageable pageable) {
        log.info("Fetching EClient with pageable: {}", pageable);
        return eClientService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EClientDTO> getById(@PathVariable String id) {
        return eClientService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EClientDTO> create(@Valid @RequestBody EClientDTO dto) {
        EClientDTO created = eClientService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EClientDTO> update(
            @PathVariable String id,
            @Valid @RequestBody EClientDTO dto) {
        try {
            EClientDTO updated = eClientService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EClientDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody EClientDTO dto) {
        try {
            EClientDTO updated = eClientService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            eClientService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = eClientService.exists(id);
        return ResponseEntity.ok(exists);
    }
}