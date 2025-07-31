package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, String>, JpaSpecificationExecutor<Log> {

    // Find logs by level
    List<Log> findByLevel(Log.LogLevel level);
    
    // Find logs by action
    List<Log> findByAction(Log.LogAction action);
    
    // Find logs by entity
    List<Log> findByEntity(String entity);
    
    // Find logs by user
    List<Log> findByUserId(String userId);
    
    // Find logs by entity and entity ID
    List<Log> findByEntityAndEntityId(String entity, String entityId);
    
    // Find logs within date range
    @Query("SELECT l FROM Log l WHERE l.createdAt BETWEEN :startDate AND :endDate ORDER BY l.createdAt DESC")
    Page<Log> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    // Find logs by user and date range
    @Query("SELECT l FROM Log l WHERE l.userId = :userId AND l.createdAt BETWEEN :startDate AND :endDate ORDER BY l.createdAt DESC")
    Page<Log> findByUserIdAndDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    // Find error logs
    @Query("SELECT l FROM Log l WHERE l.level = 'ERROR' ORDER BY l.createdAt DESC")
    Page<Log> findErrorLogs(Pageable pageable);
    
    // Find recent logs for an entity
    @Query("SELECT l FROM Log l WHERE l.entity = :entity AND l.entityId = :entityId ORDER BY l.createdAt DESC")
    Page<Log> findRecentLogsByEntity(String entity, String entityId, Pageable pageable);
    
    // Count logs by level in the last 24 hours
    @Query("SELECT COUNT(l) FROM Log l WHERE l.level = :level AND l.createdAt >= :since")
    Long countByLevelSince(Log.LogLevel level, LocalDateTime since);
    
    // Find logs by endpoint
    List<Log> findByEndpoint(String endpoint);
    
    // Find logs by HTTP method
    List<Log> findByHttpMethod(String httpMethod);
    
    // Find logs with response status >= 400 (errors)
    @Query("SELECT l FROM Log l WHERE l.responseStatus >= 400 ORDER BY l.createdAt DESC")
    Page<Log> findHttpErrorLogs(Pageable pageable);
}
