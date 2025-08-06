package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.AbsenceDTO;
import com.Clientin.Clientin.service.AbsenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/absences")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Absence Management", description = "APIs for managing employee absences")
public class AbsenceController {

    private final AbsenceService absenceService;

    @GetMapping
    @Operation(summary = "Get all absences with pagination")
    public Page<AbsenceDTO> getAll(Pageable pageable) {
        log.info("Fetching Absence with pageable: {}", pageable);
        return absenceService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get absence by ID")
    public ResponseEntity<AbsenceDTO> getById(
            @Parameter(description = "Absence ID") @PathVariable String id) {
        return absenceService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new absence request")
    public ResponseEntity<AbsenceDTO> create(@Valid @RequestBody AbsenceDTO dto) {
        AbsenceDTO created = absenceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update absence")
    public ResponseEntity<AbsenceDTO> update(
            @PathVariable String id,
            @Valid @RequestBody AbsenceDTO dto) {
        try {
            AbsenceDTO updated = absenceService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update absence")
    public ResponseEntity<AbsenceDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody AbsenceDTO dto) {
        try {
            AbsenceDTO updated = absenceService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete absence")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            absenceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Business-specific endpoints
    @PostMapping("/{id}/approve")
    @Operation(summary = "Approve absence request")
    public ResponseEntity<AbsenceDTO> approve(
            @PathVariable String id,
            @RequestParam @NotBlank String approverId) {
        try {
            AbsenceDTO approved = absenceService.approve(id, approverId);
            return ResponseEntity.ok(approved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "Reject absence request")
    public ResponseEntity<AbsenceDTO> reject(
            @PathVariable String id,
            @RequestParam @NotBlank String approverId,
            @RequestParam String reason) {
        try {
            AbsenceDTO rejected = absenceService.reject(id, approverId, reason);
            return ResponseEntity.ok(rejected);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get absences by employee ID")
    public ResponseEntity<List<AbsenceDTO>> getByEmployeeId(
            @PathVariable String employeeId) {
        List<AbsenceDTO> absences = absenceService.findByEmployeeId(employeeId);
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/pending")
    @Operation(summary = "Get pending approval absences")
    public ResponseEntity<List<AbsenceDTO>> getPendingApprovals() {
        List<AbsenceDTO> pending = absenceService.findPendingApprovals();
        return ResponseEntity.ok(pending);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get absences within date range")
    public ResponseEntity<List<AbsenceDTO>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AbsenceDTO> absences = absenceService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(absences);
    }

    @PostMapping("/bulk-approve")
    @Operation(summary = "Bulk approve absences")
    public ResponseEntity<List<AbsenceDTO>> bulkApprove(
            @RequestBody List<String> ids,
            @RequestParam @NotBlank String approverId) {
        try {
            List<AbsenceDTO> approved = absenceService.bulkApprove(ids, approverId);
            return ResponseEntity.ok(approved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/exists/{id}")
    @Operation(summary = "Check if absence exists")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = absenceService.exists(id);
        return ResponseEntity.ok(exists);
    }
}