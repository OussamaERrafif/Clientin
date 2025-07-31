package com.Clientin.Clientin.service;

import com.Clientin.Clientin.entity.Log;
import com.Clientin.Clientin.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final LogRepository logRepository;

    @Transactional
    public Log createLog(Log logEntity) {
        try {
            enrichLogWithRequestInfo(logEntity);
            return logRepository.save(logEntity);
        } catch (Exception e) {
            log.error("Failed to save log entry: {}", e.getMessage());
            return null;
        }
    }

    @Transactional
    public Log logAction(Log.LogLevel level, Log.LogAction action, String entity, String message) {
        Log logEntity = new Log(level, action, entity, message);
        return createLog(logEntity);
    }

    @Transactional
    public Log logAction(Log.LogLevel level, Log.LogAction action, String entity, String entityId, String userId, String message) {
        Log logEntity = new Log(level, action, entity, entityId, userId, message);
        return createLog(logEntity);
    }

    @Transactional
    public Log logAction(Log.LogLevel level, Log.LogAction action, String entity, String entityId, String userId, String message, String details) {
        Log logEntity = new Log(level, action, entity, entityId, userId, message);
        logEntity.setDetails(details);
        return createLog(logEntity);
    }

    @Transactional
    public Log logWithHttpInfo(Log.LogLevel level, Log.LogAction action, String entity, String entityId, String userId, String message, String endpoint, String httpMethod, Integer responseStatus, Long executionTimeMs) {
        Log logEntity = new Log(level, action, entity, entityId, userId, message);
        logEntity.setEndpoint(endpoint);
        logEntity.setHttpMethod(httpMethod);
        logEntity.setResponseStatus(responseStatus);
        logEntity.setExecutionTimeMs(executionTimeMs);
        return createLog(logEntity);
    }

    // Convenience methods for common operations
    @Transactional
    public Log logCreate(String entity, String entityId, String userId, String message) {
        return logAction(Log.LogLevel.INFO, Log.LogAction.CREATE, entity, entityId, userId, 
                        String.format("Created %s with ID: %s. %s", entity, entityId, message));
    }

    @Transactional
    public Log logUpdate(String entity, String entityId, String userId, String message) {
        return logAction(Log.LogLevel.INFO, Log.LogAction.UPDATE, entity, entityId, userId, 
                        String.format("Updated %s with ID: %s. %s", entity, entityId, message));
    }

    @Transactional
    public Log logDelete(String entity, String entityId, String userId, String message) {
        return logAction(Log.LogLevel.INFO, Log.LogAction.DELETE, entity, entityId, userId, 
                        String.format("Deleted %s with ID: %s. %s", entity, entityId, message));
    }

    @Transactional
    public Log logRead(String entity, String entityId, String userId, String message) {
        return logAction(Log.LogLevel.DEBUG, Log.LogAction.READ, entity, entityId, userId, 
                        String.format("Read %s with ID: %s. %s", entity, entityId, message));
    }

    @Transactional
    public Log logError(String entity, String entityId, String userId, String message, String details) {
        return logAction(Log.LogLevel.ERROR, Log.LogAction.PROCESS, entity, entityId, userId, message, details);
    }

    @Transactional
    public Log logWarning(String entity, String entityId, String userId, String message) {
        return logAction(Log.LogLevel.WARN, Log.LogAction.PROCESS, entity, entityId, userId, message);
    }

    // Query methods
    public Page<Log> findAll(Pageable pageable) {
        return logRepository.findAll(pageable);
    }

    public List<Log> findByLevel(Log.LogLevel level) {
        return logRepository.findByLevel(level);
    }

    public List<Log> findByAction(Log.LogAction action) {
        return logRepository.findByAction(action);
    }

    public List<Log> findByEntity(String entity) {
        return logRepository.findByEntity(entity);
    }

    public List<Log> findByUserId(String userId) {
        return logRepository.findByUserId(userId);
    }

    public Page<Log> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return logRepository.findByDateRange(startDate, endDate, pageable);
    }

    public Page<Log> findErrorLogs(Pageable pageable) {
        return logRepository.findErrorLogs(pageable);
    }

    public Page<Log> findRecentLogsByEntity(String entity, String entityId, Pageable pageable) {
        return logRepository.findRecentLogsByEntity(entity, entityId, pageable);
    }

    public Long countErrorsSince(LocalDateTime since) {
        return logRepository.countByLevelSince(Log.LogLevel.ERROR, since);
    }

    public Long countWarningsSince(LocalDateTime since) {
        return logRepository.countByLevelSince(Log.LogLevel.WARN, since);
    }

    // Helper method to enrich log with request information
    private void enrichLogWithRequestInfo(Log logEntity) {
        try {
            ServletRequestAttributes requestAttributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                
                logEntity.setIpAddress(getClientIpAddress(request));
                logEntity.setUserAgent(request.getHeader("User-Agent"));
                
                if (logEntity.getEndpoint() == null) {
                    logEntity.setEndpoint(request.getRequestURI());
                }
                
                if (logEntity.getHttpMethod() == null) {
                    logEntity.setHttpMethod(request.getMethod());
                }
                
                // You can add session ID if needed
                if (request.getSession(false) != null) {
                    logEntity.setSessionId(request.getSession().getId());
                }
            }
        } catch (Exception e) {
            log.debug("Could not enrich log with request info: {}", e.getMessage());
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}
