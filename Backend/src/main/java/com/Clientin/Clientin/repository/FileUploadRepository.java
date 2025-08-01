package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.FileUpload;
import com.Clientin.Clientin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface FileUploadRepository 
    extends JpaRepository<FileUpload, String> {

    // Automatic query methods
    List<FileUpload> findByUserId(String userId);
    List<FileUpload> findByEntityTypeAndEntityId(String entityType, String entityId);
    List<FileUpload> findByFileType(FileUpload.FileType fileType);
    List<FileUpload> findByIsPublic(Boolean isPublic);
    
    // Custom JPQL queries
    @Query("SELECT e FROM FileUpload e WHERE e.expiresAt < :now")
    List<FileUpload> findExpiredFiles(LocalDateTime now);

    @Query("SELECT e FROM FileUpload e WHERE e.userId = :userId AND e.fileType = :fileType")
    List<FileUpload> findByUserIdAndFileType(String userId, FileUpload.FileType fileType);
    
    @Query("SELECT SUM(e.fileSize) FROM FileUpload e WHERE e.userId = :userId")
    Long getTotalFileSizeByUser(String userId);
}