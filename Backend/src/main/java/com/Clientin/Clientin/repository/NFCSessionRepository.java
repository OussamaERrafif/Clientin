package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.NFCSession;
import com.Clientin.Clientin.entity.NFCDevice;
import com.Clientin.Clientin.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface NFCSessionRepository 
    extends JpaRepository<NFCSession, String>, JpaSpecificationExecutor<NFCSession> {

    // Automatic query methods
    List<NFCSession> findByDeviceId(String deviceId);
    Optional<NFCSession> findBySessionToken(String sessionToken);
    List<NFCSession> findByStatus(NFCSession.SessionStatus status);
    List<NFCSession> findByClientId(String clientId);
    
    // Custom JPQL queries
    @Query("SELECT e FROM NFCSession e WHERE e.deviceId = :deviceId AND e.status = :status")
    List<NFCSession> findByDeviceIdAndStatus(String deviceId, NFCSession.SessionStatus status);

    @Query("SELECT e FROM NFCSession e WHERE e.expiresAt < :now AND e.status = 'ACTIVE'")
    List<NFCSession> findExpiredActiveSessions(LocalDateTime now);
    
    @Query("SELECT e FROM NFCSession e WHERE e.createdAt BETWEEN :startDate AND :endDate")
    List<NFCSession> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}