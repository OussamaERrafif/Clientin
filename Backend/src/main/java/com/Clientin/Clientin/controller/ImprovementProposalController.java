package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.ImprovementProposalDTO;
import com.Clientin.Clientin.service.ImprovementProposalService;
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
@RequestMapping("/api/v1/improvementProposals")
@RequiredArgsConstructor
@Slf4j
public class ImprovementProposalController {

    private final ImprovementProposalService improvementProposalService;

    @GetMapping
    public Page<ImprovementProposalDTO> getAll(Pageable pageable) {
        log.info("Fetching ImprovementProposal with pageable: {}", pageable);
        return improvementProposalService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImprovementProposalDTO> getById(@PathVariable String id) {
        return improvementProposalService.findById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ImprovementProposalDTO> create(@Valid @RequestBody ImprovementProposalDTO dto) {
        ImprovementProposalDTO created = improvementProposalService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImprovementProposalDTO> update(
            @PathVariable String id,
            @Valid @RequestBody ImprovementProposalDTO dto) {
        try {
            ImprovementProposalDTO updated = improvementProposalService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ImprovementProposalDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody ImprovementProposalDTO dto) {
        try {
            ImprovementProposalDTO updated = improvementProposalService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            improvementProposalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = improvementProposalService.exists(id);
        return ResponseEntity.ok(exists);
    }
}