package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.annotation.Loggable;
import com.Clientin.Clientin.dto.UserDTO;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.exception.ResourceNotFoundException;
import com.Clientin.Clientin.exception.ServiceException;
import com.Clientin.Clientin.service.LogService;
import com.Clientin.Clientin.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final LogService logService;

    @GetMapping
    @Loggable(action = Log.LogAction.READ, entity = "User", message = "Fetching all users")
    public Page<UserDTO> getAll(Pageable pageable) {
        log.info("Fetching User with pageable: {}", pageable);
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "User", message = "Fetching user by ID", logParams = true)
    public ResponseEntity<UserDTO> getById(@PathVariable String id) {
        logService.logRead("User", id, "system", "Attempting to fetch user by ID");
        return userService.findById(id)
                .map(dto -> {
                    logService.logRead("User", id, "system", "Successfully fetched user");
                    return ResponseEntity.ok(dto);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @PostMapping
    @Loggable(action = Log.LogAction.CREATE, entity = "User", message = "Creating new user", logParams = true, logReturn = true)
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) {
        logService.logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, "User", null, "system", "Attempting to create new user: " + dto.getEmail());
        UserDTO created = userService.create(dto);
        logService.logCreate("User", created.getId(), "system", "User created successfully with email: " + created.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "User", message = "Updating user", logParams = true)
    public ResponseEntity<UserDTO> update(
            @PathVariable String id,
            @Valid @RequestBody UserDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "User", id, "system", "Attempting to update user");
            UserDTO updated = userService.update(id, dto);
            logService.logUpdate("User", id, "system", "User updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            throw new ServiceException("User", "update", "Failed to update user with ID: " + id, e);
        }
    }

    @PatchMapping("/{id}")
    @Loggable(action = Log.LogAction.UPDATE, entity = "User", message = "Partially updating user", logParams = true)
    public ResponseEntity<UserDTO> partialUpdate(
            @PathVariable String id,
            @Valid @RequestBody UserDTO dto) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, "User", id, "system", "Attempting partial update of user");
            UserDTO updated = userService.partialUpdate(id, dto);
            logService.logUpdate("User", id, "system", "User partially updated successfully");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            throw new ServiceException("User", "partialUpdate", "Failed to partially update user with ID: " + id, e);
        }
    }

    @DeleteMapping("/{id}")
    @Loggable(action = Log.LogAction.DELETE, entity = "User", message = "Deleting user", logParams = true)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            logService.logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, "User", id, "system", "Attempting to delete user");
            userService.delete(id);
            logService.logDelete("User", id, "system", "User deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ServiceException("User", "delete", "Failed to delete user with ID: " + id, e);
        }
    }

    @GetMapping("/exists/{id}")
    @Loggable(action = Log.LogAction.READ, entity = "User", message = "Checking user existence", logParams = true, logReturn = true)
    public ResponseEntity<Boolean> exists(@PathVariable String id) {
        boolean exists = userService.exists(id);
        logService.logRead("User", id, "system", "Checked user existence: " + exists);
        return ResponseEntity.ok(exists);
    }
}