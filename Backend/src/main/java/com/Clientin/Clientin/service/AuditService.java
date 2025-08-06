package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.AuditLogDTO;
import com.Clientin.Clientin.entity.AuditLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Service for audit logging and compliance tracking
 */
public interface AuditService {
    
    /**
     * Log user action
     */
    void logAction(String userId, String entityType, String entityId, AuditLog.Action action, 
                  String oldValues, String newValues);
    
    /**
     * Log authentication attempt
     */
    void logAuthAttempt(String userId, AuditLog.Action action, String ipAddress, String userAgent, boolean success);
    
    /**
     * Get audit trail for entity
     */
    List<AuditLogDTO> getEntityAuditTrail(String entityType, String entityId);
    
    /**
     * Get user activity log
     */
    List<AuditLogDTO> getUserActivityLog(String userId, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Search audit logs
     */
    List<AuditLogDTO> searchAuditLogs(String entityType, AuditLog.Action action, 
                                     LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Get security events
     */
    List<AuditLogDTO> getSecurityEvents(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Archive old audit logs
     */
    void archiveOldLogs(int daysOld);
    
    /**
     * Generate compliance report
     */
    Map<String, Object> generateComplianceReport(LocalDateTime startDate, LocalDateTime endDate);
}
