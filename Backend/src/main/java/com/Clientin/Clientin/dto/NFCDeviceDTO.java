package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.NFCDevice;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NFCDeviceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String deviceName;
    private String deviceSerial;
    private String location;
    private NFCDevice.DeviceStatus status;
    private String firmwareVersion;
    private Integer batteryLevel;
    private LocalDateTime lastPing;
    private String configuration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}