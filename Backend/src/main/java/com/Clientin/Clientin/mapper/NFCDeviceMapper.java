package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.NFCDevice;
import com.Clientin.Clientin.dto.NFCDeviceDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NFCDeviceMapper {

    public NFCDevice toEntity(NFCDeviceDTO dto) {
        if (dto == null) return null;
        
        NFCDevice entity = new NFCDevice();
        entity.setId(dto.getId());
        entity.setDeviceName(dto.getDeviceName());
        entity.setDeviceSerial(dto.getDeviceSerial());
        entity.setLocation(dto.getLocation());
        entity.setStatus(dto.getStatus());
        entity.setFirmwareVersion(dto.getFirmwareVersion());
        entity.setBatteryLevel(dto.getBatteryLevel());
        entity.setLastPing(dto.getLastPing());
        entity.setConfiguration(dto.getConfiguration());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public NFCDeviceDTO toDTO(NFCDevice entity) {
        if (entity == null) return null;
        
        NFCDeviceDTO dto = new NFCDeviceDTO();
        dto.setId(entity.getId());
        dto.setDeviceName(entity.getDeviceName());
        dto.setDeviceSerial(entity.getDeviceSerial());
        dto.setLocation(entity.getLocation());
        dto.setStatus(entity.getStatus());
        dto.setFirmwareVersion(entity.getFirmwareVersion());
        dto.setBatteryLevel(entity.getBatteryLevel());
        dto.setLastPing(entity.getLastPing());
        dto.setConfiguration(entity.getConfiguration());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<NFCDeviceDTO> toDTOList(List<NFCDevice> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<NFCDevice> toEntityList(List<NFCDeviceDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(NFCDeviceDTO dto, NFCDevice entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getDeviceName() != null) {
            entity.setDeviceName(dto.getDeviceName());
        }
        if (dto.getDeviceSerial() != null) {
            entity.setDeviceSerial(dto.getDeviceSerial());
        }
        if (dto.getLocation() != null) {
            entity.setLocation(dto.getLocation());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getFirmwareVersion() != null) {
            entity.setFirmwareVersion(dto.getFirmwareVersion());
        }
        if (dto.getBatteryLevel() != null) {
            entity.setBatteryLevel(dto.getBatteryLevel());
        }
        if (dto.getLastPing() != null) {
            entity.setLastPing(dto.getLastPing());
        }
        if (dto.getConfiguration() != null) {
            entity.setConfiguration(dto.getConfiguration());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}