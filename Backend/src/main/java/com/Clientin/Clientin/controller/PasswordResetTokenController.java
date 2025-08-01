package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.PasswordResetTokenDTO;
import com.Clientin.Clientin.service.PasswordResetTokenService;
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
@RequestMapping("/api/v1/passwordResetTokens")
@RequiredArgsConstructor
@Slf4j
public class PasswordResetTokenController {

    private final PasswordResetTokenService passwordResetTokenService;

    @GetMapping
    public Page<PasswordResetTokenDTO> getAll(Pageable pageable) {
        log.info("Fetching PasswordResetToken with pageable: {}", pageable);
        return passwordResetTokenService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordResetTokenDTO> getById(@PathVariable String id) {
        return passwordResetTokenService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PasswordResetTokenDTO> create(@Valid @RequestBody PasswordResetTokenDTO dto) {
        PasswordResetTokenDTO created = passwordResetTokenService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PasswordResetTokenDTO> update(
            @PathVariable String id,
            @Valid @RequestBody PasswordResetTokenDTO dto) {
        try {
            PasswordResetTokenDTO updated = passwordResetTokenService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PasswordResetTokenDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody PasswordResetTokenDTO dto) {
        try {
            PasswordResetTokenDTO updated = passwordResetTokenService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            passwordResetTokenService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = passwordResetTokenService.exists(id);
        return ResponseEntity.ok(exists);
    }
}