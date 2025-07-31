package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.FeedbackDTO;
import com.Clientin.Clientin.service.FeedbackService;
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
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public Page<FeedbackDTO> getAll(Pageable pageable) {
        log.info("Fetching Feedback with pageable: {}", pageable);
        return feedbackService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getById(@PathVariable String id) {
        return feedbackService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FeedbackDTO> create(@Valid @RequestBody FeedbackDTO dto) {
        FeedbackDTO created = feedbackService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> update(
            @PathVariable String id,
            @Valid @RequestBody FeedbackDTO dto) {
        try {
            FeedbackDTO updated = feedbackService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FeedbackDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody FeedbackDTO dto) {
        try {
            FeedbackDTO updated = feedbackService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            feedbackService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = feedbackService.exists(id);
        return ResponseEntity.ok(exists);
    }
}