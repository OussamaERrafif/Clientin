package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.AuditLog;
import com.Clientin.Clientin.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private String entityType;
    private String entityId;
    private AuditLog.Action action;
    private String oldValues;
    private String newValues;
    private String ipAddress;
    private String userAgent;
    private String sessionId;
    private String requestId;
    private LocalDateTime createdAt;
    // Relationship handled via userId
}