package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.NFCDeviceDTO;
import com.Clientin.Clientin.dto.NFCSessionDTO;
import com.Clientin.Clientin.dto.FeedbackDTO;

/**
 * Service for handling NFC-specific operations
 */
public interface NFCService {
    
    /**
     * Create new NFC feedback session
     */
    NFCSessionDTO createFeedbackSession(String deviceId);
    
    /**
     * Complete feedback session with rating and comment
     */
    FeedbackDTO completeFeedbackSession(String sessionToken, Integer rating, String comment, Boolean isAnonymous);
    
    /**
     * Get active session by token
     */
    NFCSessionDTO getActiveSession(String sessionToken);
    
    /**
     * Register new NFC device
     */
    NFCDeviceDTO registerDevice(NFCDeviceDTO deviceDTO);
    
    /**
     * Update device status and ping
     */
    void updateDeviceStatus(String deviceId, String status, Integer batteryLevel);
    
    /**
     * Get device configuration
     */
    String getDeviceConfiguration(String deviceId);
    
    /**
     * Update device configuration
     */
    void updateDeviceConfiguration(String deviceId, String configuration);
}
