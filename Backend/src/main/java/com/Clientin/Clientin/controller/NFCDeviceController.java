package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.NFCDeviceDTO;
import com.Clientin.Clientin.service.NFCDeviceService;
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
@RequestMapping("/api/v1/nFCDevices")
@RequiredArgsConstructor
@Slf4j
public class NFCDeviceController {

    private final NFCDeviceService nFCDeviceService;

    @GetMapping
    public Page<NFCDeviceDTO> getAll(Pageable pageable) {
        log.info("Fetching NFCDevice with pageable: {}", pageable);
        return nFCDeviceService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NFCDeviceDTO> getById(@PathVariable String id) {
        return nFCDeviceService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NFCDeviceDTO> create(@Valid @RequestBody NFCDeviceDTO dto) {
        NFCDeviceDTO created = nFCDeviceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NFCDeviceDTO> update(
            @PathVariable String id,
            @Valid @RequestBody NFCDeviceDTO dto) {
        try {
            NFCDeviceDTO updated = nFCDeviceService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NFCDeviceDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody NFCDeviceDTO dto) {
        try {
            NFCDeviceDTO updated = nFCDeviceService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            nFCDeviceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = nFCDeviceService.exists(id);
        return ResponseEntity.ok(exists);
    }
}