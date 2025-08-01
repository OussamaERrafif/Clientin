package com.Clientin.Clientin.mapper;

import com.Clientin.Clientin.entity.FileUpload;
import com.Clientin.Clientin.dto.FileUploadDTO;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileUploadMapper {

    public FileUpload toEntity(FileUploadDTO dto) {
        if (dto == null) return null;
        
        FileUpload entity = new FileUpload();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setOriginalFilename(dto.getOriginalFilename());
        entity.setStoredFilename(dto.getStoredFilename());
        entity.setFilePath(dto.getFilePath());
        entity.setContentType(dto.getContentType());
        entity.setFileSize(dto.getFileSize());
        entity.setFileType(dto.getFileType());
        entity.setEntityType(dto.getEntityType());
        entity.setEntityId(dto.getEntityId());
        entity.setFileHash(dto.getFileHash());
        entity.setIsPublic(dto.getIsPublic());
        entity.setDownloadCount(dto.getDownloadCount());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setExpiresAt(dto.getExpiresAt());
        entity.setUser(dto.getUser());
        return entity;
    }

    public FileUploadDTO toDTO(FileUpload entity) {
        if (entity == null) return null;
        
        FileUploadDTO dto = new FileUploadDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setOriginalFilename(entity.getOriginalFilename());
        dto.setStoredFilename(entity.getStoredFilename());
        dto.setFilePath(entity.getFilePath());
        dto.setContentType(entity.getContentType());
        dto.setFileSize(entity.getFileSize());
        dto.setFileType(entity.getFileType());
        dto.setEntityType(entity.getEntityType());
        dto.setEntityId(entity.getEntityId());
        dto.setFileHash(entity.getFileHash());
        dto.setIsPublic(entity.getIsPublic());
        dto.setDownloadCount(entity.getDownloadCount());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setExpiresAt(entity.getExpiresAt());
        dto.setUser(entity.getUser());
        return dto;
    }

    public List<FileUploadDTO> toDTOList(List<FileUpload> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<FileUpload> toEntityList(List<FileUploadDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public void partialUpdate(FileUploadDTO dto, FileUpload entity) {
        if (dto == null || entity == null) return;

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }
        if (dto.getOriginalFilename() != null) {
            entity.setOriginalFilename(dto.getOriginalFilename());
        }
        if (dto.getStoredFilename() != null) {
            entity.setStoredFilename(dto.getStoredFilename());
        }
        if (dto.getFilePath() != null) {
            entity.setFilePath(dto.getFilePath());
        }
        if (dto.getContentType() != null) {
            entity.setContentType(dto.getContentType());
        }
        if (dto.getFileSize() != null) {
            entity.setFileSize(dto.getFileSize());
        }
        if (dto.getFileType() != null) {
            entity.setFileType(dto.getFileType());
        }
        if (dto.getEntityType() != null) {
            entity.setEntityType(dto.getEntityType());
        }
        if (dto.getEntityId() != null) {
            entity.setEntityId(dto.getEntityId());
        }
        if (dto.getFileHash() != null) {
            entity.setFileHash(dto.getFileHash());
        }
        if (dto.getIsPublic() != null) {
            entity.setIsPublic(dto.getIsPublic());
        }
        if (dto.getDownloadCount() != null) {
            entity.setDownloadCount(dto.getDownloadCount());
        }
        if (dto.getCreatedAt() != null) {
            entity.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getExpiresAt() != null) {
            entity.setExpiresAt(dto.getExpiresAt());
        }
        if (dto.getUser() != null) {
            entity.setUser(dto.getUser());
        }
    }
}