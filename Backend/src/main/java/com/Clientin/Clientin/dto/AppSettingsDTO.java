package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.AppSettings;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppSettingsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String settingKey;
    private String settingValue;
    private AppSettings.SettingType settingType;
    private String category;
    private String description;
    private String defaultValue;
    private String validationRules;
    private Boolean isPublic;
    private Boolean isEncrypted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}