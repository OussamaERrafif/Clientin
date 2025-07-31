package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.EmployeeProfileDTO;
import com.Clientin.Clientin.service.EmployeeProfileService;
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
@RequestMapping("/api/v1/employeeProfiles")
@RequiredArgsConstructor
@Slf4j
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    @GetMapping
    public Page<EmployeeProfileDTO> getAll(Pageable pageable) {
        log.info("Fetching EmployeeProfile with pageable: {}", pageable);
        return employeeProfileService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProfileDTO> getById(@PathVariable String id) {
        return employeeProfileService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeProfileDTO> create(@Valid @RequestBody EmployeeProfileDTO dto) {
        EmployeeProfileDTO created = employeeProfileService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeProfileDTO> update(
            @PathVariable String id,
            @Valid @RequestBody EmployeeProfileDTO dto) {
        try {
            EmployeeProfileDTO updated = employeeProfileService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeProfileDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody EmployeeProfileDTO dto) {
        try {
            EmployeeProfileDTO updated = employeeProfileService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            employeeProfileService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = employeeProfileService.exists(id);
        return ResponseEntity.ok(exists);
    }
}