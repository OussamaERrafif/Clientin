package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.NFCSession;
import com.Clientin.Clientin.entity.NFCDevice;
import com.Clientin.Clientin.entity.Client;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NFCSessionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String deviceId;
    private String sessionToken;
    private String clientId;
    private NFCSession.SessionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime completedAt;
    private String metadata;
    // Relationship handled via deviceId
    // Relationship handled via clientId
}