package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.AuthToken;
import com.Clientin.Clientin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface AuthTokenRepository 
    extends JpaRepository<AuthToken, String>, JpaSpecificationExecutor<AuthToken> {

    // Automatic query methods
    List<AuthToken> findByUserId(String userId);
    Optional<AuthToken> findByTokenHash(String tokenHash);
    List<AuthToken> findByTokenType(AuthToken.TokenType tokenType);
    List<AuthToken> findByRevoked(Boolean revoked);
    
    // Custom JPQL queries
    @Query("SELECT e FROM AuthToken e WHERE e.userId = :userId AND e.tokenType = :tokenType AND e.revoked = false")
    List<AuthToken> findActiveTokensByUserAndType(String userId, AuthToken.TokenType tokenType);

    @Query("SELECT e FROM AuthToken e WHERE e.expiresAt < :now")
    List<AuthToken> findExpiredTokens(LocalDateTime now);
    
    @Modifying
    @Query("UPDATE AuthToken e SET e.revoked = true WHERE e.userId = :userId")
    void revokeAllUserTokens(String userId);
    
    @Modifying
    @Query("UPDATE AuthToken e SET e.revoked = true WHERE e.expiresAt < :now")
    void revokeExpiredTokens(LocalDateTime now);
}