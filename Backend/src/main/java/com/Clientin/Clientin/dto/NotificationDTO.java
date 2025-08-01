package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.Notification;
import com.Clientin.Clientin.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private String title;
    private String message;
    private Notification.NotificationType type;
    private Notification.Priority priority;
    private Boolean readStatus;
    private LocalDateTime readAt;
    private String actionUrl;
    private String metadata;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    // Relationship handled via userId
}