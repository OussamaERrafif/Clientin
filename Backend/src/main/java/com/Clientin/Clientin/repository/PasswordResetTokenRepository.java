package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.PasswordResetToken;
import com.Clientin.Clientin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface PasswordResetTokenRepository 
    extends JpaRepository<PasswordResetToken, String> {

    // Automatic query methods
    List<PasswordResetToken> findByUserId(String userId);
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
    List<PasswordResetToken> findByUsed(Boolean used);
    
    // Custom JPQL queries
    @Query("SELECT e FROM PasswordResetToken e WHERE e.tokenHash = :tokenHash AND e.used = false AND e.expiresAt > :now")
    Optional<PasswordResetToken> findValidToken(String tokenHash, LocalDateTime now);

    @Query("SELECT e FROM PasswordResetToken e WHERE e.expiresAt < :now")
    List<PasswordResetToken> findExpiredTokens(LocalDateTime now);
    
    @Query("SELECT e FROM PasswordResetToken e WHERE e.userId = :userId AND e.used = false")
    List<PasswordResetToken> findActiveTokensByUserId(String userId);
}