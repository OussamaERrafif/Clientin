package com.Clientin.Clientin.dto;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import com.Clientin.Clientin.entity.FileUpload;
import com.Clientin.Clientin.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;
    private String originalFilename;
    private String storedFilename;
    private String filePath;
    private String contentType;
    private Long fileSize;
    private FileUpload.FileType fileType;
    private String entityType;
    private String entityId;
    private String fileHash;
    private Boolean isPublic;
    private Long downloadCount;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    // Relationship handled via userId
}