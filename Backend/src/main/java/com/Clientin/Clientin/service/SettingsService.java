package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.AppSettingsDTO;

import java.util.List;
import java.util.Map;

/**
 * Service for managing application settings and configuration
 */
public interface SettingsService {
    
    /**
     * Get setting value by key
     */
    String getSettingValue(String key);
    
    /**
     * Get setting value with default
     */
    String getSettingValue(String key, String defaultValue);
    
    /**
     * Update setting value
     */
    void updateSetting(String key, String value);
    
    /**
     * Get all public settings
     */
    Map<String, String> getPublicSettings();
    
    /**
     * Get settings by category
     */
    List<AppSettingsDTO> getSettingsByCategory(String category);
    
    /**
     * Get boolean setting
     */
    Boolean getBooleanSetting(String key, Boolean defaultValue);
    
    /**
     * Get integer setting
     */
    Integer getIntegerSetting(String key, Integer defaultValue);
    
    /**
     * Create or update setting
     */
    AppSettingsDTO createOrUpdateSetting(AppSettingsDTO settingDTO);
    
    /**
     * Reset setting to default value
     */
    void resetSettingToDefault(String key);
    
    /**
     * Validate setting value against rules
     */
    boolean validateSettingValue(String key, String value);
}
