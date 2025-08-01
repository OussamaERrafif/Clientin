package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.AuthTokenDTO;
import com.Clientin.Clientin.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Service for handling authentication and authorization operations
 */
public interface AuthService {
    
    /**
     * Authenticate user with email and password
     */
    AuthTokenDTO authenticate(String email, String password);
    
    /**
     * Generate new access token from refresh token
     */
    AuthTokenDTO refreshToken(String refreshToken);
    
    /**
     * Revoke all tokens for a user
     */
    void revokeAllTokens(String userId);
    
    /**
     * Validate token and return user details
     */
    Optional<UserDetails> validateToken(String token);
    
    /**
     * Register new user
     */
    UserDTO register(UserDTO userDTO, String password);
    
    /**
     * Initiate password reset process
     */
    void initiatePasswordReset(String email);
    
    /**
     * Complete password reset with token
     */
    void resetPassword(String token, String newPassword);
    
    /**
     * Change user password
     */
    void changePassword(String userId, String oldPassword, String newPassword);
    
    /**
     * Verify user email with token
     */
    void verifyEmail(String token);
}
