package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.NotificationPreference;
import com.Clientin.Clientin.dto.NotificationPreferenceDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationPreferenceMapper {

    public NotificationPreference toEntity(NotificationPreferenceDTO dto) {
        if (dto == null) return null;
        
        NotificationPreference entity = new NotificationPreference();
        entity.setUserId(dto.getUserId());
        entity.setEmailAlerts(dto.getEmailAlerts());
        entity.setPushNotifications(dto.getPushNotifications());
        entity.setWeeklySummary(dto.getWeeklySummary());
        entity.setUser(dto.getUser());
        return entity;
    }

    public NotificationPreferenceDTO toDTO(NotificationPreference entity) {
        if (entity == null) return null;
        
        NotificationPreferenceDTO dto = new NotificationPreferenceDTO();
        dto.setUserId(entity.getUserId());
        dto.setEmailAlerts(entity.getEmailAlerts());
        dto.setPushNotifications(entity.getPushNotifications());
        dto.setWeeklySummary(entity.getWeeklySummary());
        dto.setUser(entity.getUser());
        return dto;
    }

    public List<NotificationPreferenceDTO> toDTOList(List<NotificationPreference> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationPreference> toEntityList(List<NotificationPreferenceDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(NotificationPreferenceDTO dto, NotificationPreference entity) {
        if (dto == null || entity == null) return;

        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }
        if (dto.getEmailAlerts() != null) {
            entity.setEmailAlerts(dto.getEmailAlerts());
        }
        if (dto.getPushNotifications() != null) {
            entity.setPushNotifications(dto.getPushNotifications());
        }
        if (dto.getWeeklySummary() != null) {
            entity.setWeeklySummary(dto.getWeeklySummary());
        }
        if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
    }
}