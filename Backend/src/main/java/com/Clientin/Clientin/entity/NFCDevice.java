package com.Clientin.Clientin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nfc_devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NFCDevice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "device_name", nullable = false, length = 100)
    private String deviceName;
    
    @Column(name = "device_serial", nullable = false, unique = true, length = 100)
    private String deviceSerial;
    
    @Column(name = "location", length = 200)
    private String location;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceStatus status = DeviceStatus.ACTIVE;
    
    @Column(name = "firmware_version", length = 50)
    private String firmwareVersion;
    
    @Column(name = "battery_level")
    private Integer batteryLevel;
    
    @Column(name = "last_ping")
    private LocalDateTime lastPing;
    
    @Column(name = "configuration", columnDefinition = "TEXT")
    private String configuration;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public enum DeviceStatus {
        ACTIVE, INACTIVE, MAINTENANCE, OFFLINE
    }
}
