package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.ContractDTO;
import com.Clientin.Clientin.service.ContractService;
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
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
@Slf4j
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public Page<ContractDTO> getAll(Pageable pageable) {
        log.info("Fetching Contract with pageable: {}", pageable);
        return contractService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getById(@PathVariable String id) {
        return contractService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContractDTO> create(@Valid @RequestBody ContractDTO dto) {
        ContractDTO created = contractService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDTO> update(
            @PathVariable String id,
            @Valid @RequestBody ContractDTO dto) {
        try {
            ContractDTO updated = contractService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContractDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody ContractDTO dto) {
        try {
            ContractDTO updated = contractService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            contractService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = contractService.exists(id);
        return ResponseEntity.ok(exists);
    }
}