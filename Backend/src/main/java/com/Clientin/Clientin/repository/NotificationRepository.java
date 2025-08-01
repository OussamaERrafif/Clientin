package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Notification;
import com.Clientin.Clientin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface NotificationRepository 
    extends JpaRepository<Notification, String> {

    // Automatic query methods
    List<Notification> findByUserId(String userId);
    List<Notification> findByReadStatus(Boolean readStatus);
    List<Notification> findByType(Notification.NotificationType type);
    List<Notification> findByPriority(Notification.Priority priority);
    
    // Custom JPQL queries
    @Query("SELECT e FROM Notification e WHERE e.userId = :userId AND e.readStatus = false ORDER BY e.createdAt DESC")
    List<Notification> findUnreadByUserId(String userId);

    @Query("SELECT COUNT(e) FROM Notification e WHERE e.userId = :userId AND e.readStatus = false")
    Long countUnreadByUserId(String userId);
    
    @Query("SELECT e FROM Notification e WHERE e.expiresAt < :now")
    List<Notification> findExpiredNotifications(LocalDateTime now);
    
    @Modifying
    @Query("UPDATE Notification e SET e.readStatus = true, e.readAt = :readAt WHERE e.userId = :userId")
    void markAllAsReadForUser(String userId, LocalDateTime readAt);
}