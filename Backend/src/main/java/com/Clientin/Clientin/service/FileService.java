package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.FileUploadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * Service for handling file uploads and downloads
 */
public interface FileService {
    
    /**
     * Upload file and return file info
     */
    FileUploadDTO uploadFile(MultipartFile file, String entityType, String entityId, String userId);
    
    /**
     * Download file by ID
     */
    InputStream downloadFile(String fileId);
    
    /**
     * Delete file
     */
    void deleteFile(String fileId);
    
    /**
     * Get file metadata
     */
    FileUploadDTO getFileMetadata(String fileId);
    
    /**
     * Get files for entity
     */
    List<FileUploadDTO> getEntityFiles(String entityType, String entityId);
    
    /**
     * Upload avatar/profile picture
     */
    FileUploadDTO uploadAvatar(MultipartFile file, String userId);
    
    /**
     * Generate secure download URL
     */
    String generateDownloadUrl(String fileId, int expirationMinutes);
    
    /**
     * Cleanup expired files
     */
    void cleanupExpiredFiles();
    
    /**
     * Validate file type and size
     */
    boolean validateFile(MultipartFile file, List<String> allowedTypes, long maxSizeBytes);
}
