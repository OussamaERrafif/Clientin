package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, String>, JpaSpecificationExecutor<NotificationPreference> {

    // Automatic query methods
    List<NotificationPreference> findByEmailAlerts(Boolean emailAlerts);
    List<NotificationPreference> findByPushNotifications(Boolean pushNotifications);
    List<NotificationPreference> findByWeeklySummary(Boolean weeklySummary);
    List<NotificationPreference> findByUserId(String userId);
    
    // Custom JPQL queries
    @Query("SELECT np FROM NotificationPreference np WHERE np.emailAlerts = :emailAlerts")
    List<NotificationPreference> findByEmailAlertsCustom(Boolean emailAlerts);

    @Query("SELECT np FROM NotificationPreference np WHERE np.emailAlerts = true AND np.pushNotifications = true")
    List<NotificationPreference> findActiveNotificationUsers();

    @Query("SELECT np FROM NotificationPreference np")
    List<NotificationPreference> findAllNotificationPreferences();
}