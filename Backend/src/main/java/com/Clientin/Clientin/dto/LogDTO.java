package com.Clientin.Clientin.dto;

import com.Clientin.Clientin.entity.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {
    
    private String id;
    private Log.LogLevel level;
    private Log.LogAction action;
    private String entity;
    private String entityId;
    private String userId;
    private String userEmail;
    private String userRole;
    private String message;
    private String details;
    private String requestId;
    private String sessionId;
    private String ipAddress;
    private String userAgent;
    private String endpoint;
    private String httpMethod;
    private Integer responseStatus;
    private Long executionTimeMs;
    private LocalDateTime createdAt;
}
