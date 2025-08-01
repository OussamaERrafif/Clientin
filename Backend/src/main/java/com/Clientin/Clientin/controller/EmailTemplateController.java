package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.EmailTemplateDTO;
import com.Clientin.Clientin.service.EmailTemplateService;
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
@RequestMapping("/api/v1/emailTemplates")
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @GetMapping
    public Page<EmailTemplateDTO> getAll(Pageable pageable) {
        log.info("Fetching EmailTemplate with pageable: {}", pageable);
        return emailTemplateService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> getById(@PathVariable String id) {
        return emailTemplateService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmailTemplateDTO> create(@Valid @RequestBody EmailTemplateDTO dto) {
        EmailTemplateDTO created = emailTemplateService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> update(
            @PathVariable String id,
            @Valid @RequestBody EmailTemplateDTO dto) {
        try {
            EmailTemplateDTO updated = emailTemplateService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmailTemplateDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody EmailTemplateDTO dto) {
        try {
            EmailTemplateDTO updated = emailTemplateService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            emailTemplateService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = emailTemplateService.exists(id);
        return ResponseEntity.ok(exists);
    }
}