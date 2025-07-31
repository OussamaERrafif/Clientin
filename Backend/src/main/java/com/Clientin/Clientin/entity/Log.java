package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogLevel level;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogAction action;
    
    @Column(nullable = false, length = 100)
    private String entity;
    
    @Column(name = "entity_id")
    private String entityId;
    
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "user_email")
    private String userEmail;
    
    @Column(name = "user_role")
    private String userRole;
    
    @Column(nullable = false, length = 500)
    private String message;
    
    @Column(columnDefinition = "TEXT")
    private String details;
    
    @Column(name = "request_id")
    private String requestId;
    
    @Column(name = "session_id")
    private String sessionId;
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    
    @Column(name = "endpoint", length = 200)
    private String endpoint;
    
    @Column(name = "http_method", length = 10)
    private String httpMethod;
    
    @Column(name = "response_status")
    private Integer responseStatus;
    
    @Column(name = "execution_time_ms")
    private Long executionTimeMs;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Enums
    public enum LogLevel {
        INFO, WARN, ERROR, DEBUG, TRACE
    }
    
    public enum LogAction {
        CREATE, READ, UPDATE, DELETE, LOGIN, LOGOUT, SEARCH, EXPORT, IMPORT, VALIDATE, PROCESS, SEND_EMAIL, SEND_NOTIFICATION
    }
    
    // Constructor for simple logging
    public Log(LogLevel level, LogAction action, String entity, String message) {
        this.level = level;
        this.action = action;
        this.entity = entity;
        this.message = message;
    }
    
    // Constructor with user info
    public Log(LogLevel level, LogAction action, String entity, String entityId, String userId, String message) {
        this.level = level;
        this.action = action;
        this.entity = entity;
        this.entityId = entityId;
        this.userId = userId;
        this.message = message;
    }
}
