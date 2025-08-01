package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.AppSettings;
import com.Clientin.Clientin.dto.AppSettingsDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppSettingsMapper {

    public AppSettings toEntity(AppSettingsDTO dto) {
        if (dto == null) return null;
        
        AppSettings entity = new AppSettings();
        entity.setId(dto.getId());
        entity.setSettingKey(dto.getSettingKey());
        entity.setSettingValue(dto.getSettingValue());
        entity.setSettingType(dto.getSettingType());
        entity.setCategory(dto.getCategory());
        entity.setDescription(dto.getDescription());
        entity.setDefaultValue(dto.getDefaultValue());
        entity.setValidationRules(dto.getValidationRules());
        entity.setIsPublic(dto.getIsPublic());
        entity.setIsEncrypted(dto.getIsEncrypted());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }

    public AppSettingsDTO toDTO(AppSettings entity) {
        if (entity == null) return null;
        
        AppSettingsDTO dto = new AppSettingsDTO();
        dto.setId(entity.getId());
        dto.setSettingKey(entity.getSettingKey());
        dto.setSettingValue(entity.getSettingValue());
        dto.setSettingType(entity.getSettingType());
        dto.setCategory(entity.getCategory());
        dto.setDescription(entity.getDescription());
        dto.setDefaultValue(entity.getDefaultValue());
        dto.setValidationRules(entity.getValidationRules());
        dto.setIsPublic(entity.getIsPublic());
        dto.setIsEncrypted(entity.getIsEncrypted());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<AppSettingsDTO> toDTOList(List<AppSettings> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AppSettings> toEntityList(List<AppSettingsDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(AppSettingsDTO dto, AppSettings entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getSettingKey() != null) {
            entity.setSettingKey(dto.getSettingKey());
        }
        if (dto.getSettingValue() != null) {
            entity.setSettingValue(dto.getSettingValue());
        }
        if (dto.getSettingType() != null) {
            entity.setSettingType(dto.getSettingType());
        }
        if (dto.getCategory() != null) {
            entity.setCategory(dto.getCategory());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getDefaultValue() != null) {
            entity.setDefaultValue(dto.getDefaultValue());
        }
        if (dto.getValidationRules() != null) {
            entity.setValidationRules(dto.getValidationRules());
        }
        if (dto.getIsPublic() != null) {
            entity.setIsPublic(dto.getIsPublic());
        }
        if (dto.getIsEncrypted() != null) {
            entity.setIsEncrypted(dto.getIsEncrypted());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            entity.setUpdatedAt(dto.getUpdatedAt());
        }
    }
}