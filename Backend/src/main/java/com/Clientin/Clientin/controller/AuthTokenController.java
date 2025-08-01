package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.AuthTokenDTO;
import com.Clientin.Clientin.service.AuthTokenService;
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
@RequestMapping("/api/v1/authTokens")
@RequiredArgsConstructor
@Slf4j
public class AuthTokenController {

    private final AuthTokenService authTokenService;

    @GetMapping
    public Page<AuthTokenDTO> getAll(Pageable pageable) {
        log.info("Fetching AuthToken with pageable: {}", pageable);
        return authTokenService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthTokenDTO> getById(@PathVariable String id) {
        return authTokenService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AuthTokenDTO> create(@Valid @RequestBody AuthTokenDTO dto) {
        AuthTokenDTO created = authTokenService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthTokenDTO> update(
            @PathVariable String id,
            @Valid @RequestBody AuthTokenDTO dto) {
        try {
            AuthTokenDTO updated = authTokenService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthTokenDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody AuthTokenDTO dto) {
        try {
            AuthTokenDTO updated = authTokenService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            authTokenService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = authTokenService.exists(id);
        return ResponseEntity.ok(exists);
    }
}