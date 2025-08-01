package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.AuditLog;
import com.Clientin.Clientin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface AuditLogRepository 
    extends JpaRepository<AuditLog, String> {

    // Automatic query methods
    List<AuditLog> findByUserId(String userId);
    List<AuditLog> findByEntityType(String entityType);
    List<AuditLog> findByEntityId(String entityId);
    List<AuditLog> findByAction(AuditLog.Action action);
    
    // Custom JPQL queries
    @Query("SELECT e FROM AuditLog e WHERE e.userId = :userId AND e.createdAt BETWEEN :startDate AND :endDate")
    List<AuditLog> findByUserIdAndDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT e FROM AuditLog e WHERE e.entityType = :entityType AND e.entityId = :entityId ORDER BY e.createdAt DESC")
    List<AuditLog> findEntityAuditTrail(String entityType, String entityId);
    
    @Query("SELECT e FROM AuditLog e WHERE e.action IN :actions AND e.createdAt BETWEEN :startDate AND :endDate")
    List<AuditLog> findSecurityEvents(List<AuditLog.Action> actions, LocalDateTime startDate, LocalDateTime endDate);
}