package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.FileUploadDTO;
import com.Clientin.Clientin.service.FileUploadService;
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
@RequestMapping("/api/v1/fileUploads")
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @GetMapping
    public Page<FileUploadDTO> getAll(Pageable pageable) {
        log.info("Fetching FileUpload with pageable: {}", pageable);
        return fileUploadService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileUploadDTO> getById(@PathVariable String id) {
        return fileUploadService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FileUploadDTO> create(@Valid @RequestBody FileUploadDTO dto) {
        FileUploadDTO created = fileUploadService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileUploadDTO> update(
            @PathVariable String id,
            @Valid @RequestBody FileUploadDTO dto) {
        try {
            FileUploadDTO updated = fileUploadService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FileUploadDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody FileUploadDTO dto) {
        try {
            FileUploadDTO updated = fileUploadService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            fileUploadService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = fileUploadService.exists(id);
        return ResponseEntity.ok(exists);
    }
}