package com.Clientin.Clientin.dto;

import com.Clientin.Clientin.entity.User;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferenceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private Boolean emailAlerts;
    private Boolean pushNotifications;
    private Boolean weeklySummary;
    private User user;
}