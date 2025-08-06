package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.PayslipDTO;
import com.Clientin.Clientin.service.PayslipService;
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
@RequestMapping("/api/v1/payslips")
@RequiredArgsConstructor
@Slf4j
public class PayslipController {

    private final PayslipService payslipService;

    @GetMapping
    public Page<PayslipDTO> getAll(Pageable pageable) {
        log.info("Fetching Payslip with pageable: {}", pageable);
        return payslipService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayslipDTO> getById(@PathVariable String id) {
        return payslipService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PayslipDTO> create(@Valid @RequestBody PayslipDTO dto) {
        PayslipDTO created = payslipService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayslipDTO> update(
            @PathVariable String id,
            @Valid @RequestBody PayslipDTO dto) {
        try {
            PayslipDTO updated = payslipService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PayslipDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody PayslipDTO dto) {
        try {
            PayslipDTO updated = payslipService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            payslipService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = payslipService.exists(id);
        return ResponseEntity.ok(exists);
    }
}