package com.Clientin.Clientin.controller;

import com.Clientin.Clientin.dto.LogDTO;
import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.mapper.LogMapper;
import com.Clientin.Clientin.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
@Slf4j
public class LogController {

    private final LogService logService;
    private final LogMapper logMapper;

    @GetMapping
    public Page<LogDTO> getAllLogs(Pageable pageable) {
        log.info("Fetching all logs with pageable: {}", pageable);
        logService.logRead("Log", null, null, "Fetching all logs");
        return logService.findAll(pageable)
                .map(logMapper::toDTO);
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<LogDTO>> getLogsByLevel(@PathVariable Log.LogLevel level) {
        log.info("Fetching logs by level: {}", level);
        logService.logRead("Log", null, null, "Fetching logs by level: " + level);
        List<Log> logs = logService.findByLevel(level);
        return ResponseEntity.ok(logMapper.toDTOList(logs));
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<LogDTO>> getLogsByAction(@PathVariable Log.LogAction action) {
        log.info("Fetching logs by action: {}", action);
        logService.logRead("Log", null, null, "Fetching logs by action: " + action);
        List<Log> logs = logService.findByAction(action);
        return ResponseEntity.ok(logMapper.toDTOList(logs));
    }

    @GetMapping("/entity/{entity}")
    public ResponseEntity<List<LogDTO>> getLogsByEntity(@PathVariable String entity) {
        log.info("Fetching logs by entity: {}", entity);
        logService.logRead("Log", null, null, "Fetching logs by entity: " + entity);
        List<Log> logs = logService.findByEntity(entity);
        return ResponseEntity.ok(logMapper.toDTOList(logs));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LogDTO>> getLogsByUserId(@PathVariable String userId) {
        log.info("Fetching logs by user ID: {}", userId);
        logService.logRead("Log", null, null, "Fetching logs by user ID: " + userId);
        List<Log> logs = logService.findByUserId(userId);
        return ResponseEntity.ok(logMapper.toDTOList(logs));
    }

    @GetMapping("/errors")
    public Page<LogDTO> getErrorLogs(Pageable pageable) {
        log.info("Fetching error logs with pageable: {}", pageable);
        logService.logRead("Log", null, null, "Fetching error logs");
        return logService.findErrorLogs(pageable)
                .map(logMapper::toDTO);
    }

    @GetMapping("/entity/{entity}/{entityId}")
    public Page<LogDTO> getLogsByEntityAndId(
            @PathVariable String entity,
            @PathVariable String entityId,
            Pageable pageable) {
        log.info("Fetching logs for entity: {} with ID: {}", entity, entityId);
        logService.logRead("Log", null, null, 
                          String.format("Fetching logs for entity: %s with ID: %s", entity, entityId));
        return logService.findRecentLogsByEntity(entity, entityId, pageable)
                .map(logMapper::toDTO);
    }

    @GetMapping("/date-range")
    public Page<LogDTO> getLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        log.info("Fetching logs between {} and {}", startDate, endDate);
        logService.logRead("Log", null, null, 
                          String.format("Fetching logs between %s and %s", startDate, endDate));
        return logService.findByDateRange(startDate, endDate, pageable)
                .map(logMapper::toDTO);
    }

    @GetMapping("/stats/errors/24h")
    public ResponseEntity<Long> getErrorCount24h() {
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        Long count = logService.countErrorsSince(since);
        log.info("Error count in last 24 hours: {}", count);
        logService.logRead("Log", null, null, "Fetching error count for last 24 hours: " + count);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/warnings/24h")
    public ResponseEntity<Long> getWarningCount24h() {
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        Long count = logService.countWarningsSince(since);
        log.info("Warning count in last 24 hours: {}", count);
        logService.logRead("Log", null, null, "Fetching warning count for last 24 hours: " + count);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/manual")
    public ResponseEntity<LogDTO> createManualLog(@RequestBody LogDTO logDTO) {
        log.info("Creating manual log entry: {}", logDTO.getMessage());
        Log logEntity = logMapper.toEntity(logDTO);
        Log createdLog = logService.createLog(logEntity);
        if (createdLog != null) {
            return ResponseEntity.ok(logMapper.toDTO(createdLog));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
