package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.annotation.Loggable;
import com.Clientin.Clientin.dto.ClientDTO;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.service.ClientService;
import com.Clientin.Clientin.service.LogService;
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
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final LogService logService;

    @GetMapping
    @Loggable(action = Log.LogAction.READ, entity = "Client", message = "Fetching all clients")
    public Page<ClientDTO> getAll(Pageable pageable) {
        log.info("Fetching Client with pageable: {}", pageable);
        return clientService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "Client", message = "Fetching client by ID", logParams = true)
    public ResponseEntity<ClientDTO> getById(@PathVariable String id) {
        logService.logRead("Client", id, "system", "Attempting to fetch client by ID");
        return clientService.findById(id)
                .map(dto -> {
                    logService.logRead("Client", id, "system", "Successfully fetched client");
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Loggable(action = Log.LogAction.CREATE, entity = "Client", message = "Creating new client", logParams = true, logReturn = true)
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO dto) {
        logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "Client", null, "system", 
                           "Attempting to create new client: " + dto.getName());
        ClientDTO created = clientService.create(dto);
        logService.logCreate("Client", created.getId(), "system", 
                           "Client created successfully with name: " + created.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(
            @PathVariable String id,
            @Valid @RequestBody ClientDTO dto) {
        try {
            ClientDTO updated = clientService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody ClientDTO dto) {
        try {
            ClientDTO updated = clientService.partialUpdate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            clientService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = clientService.exists(id);
        return ResponseEntity.ok(exists);
    }
}