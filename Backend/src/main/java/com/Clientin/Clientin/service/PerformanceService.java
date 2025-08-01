package com.Clientin.Clientin.service;

import com.Clientin.Clientin.dto.TrainingDTO;
import com.Clientin.Clientin.dto.PerformanceReviewDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Service for handling training programs and performance management
 */
public interface PerformanceService {
    
    /**
     * Create performance review
     */
    PerformanceReviewDTO createPerformanceReview(PerformanceReviewDTO reviewDTO);
    
    /**
     * Calculate overall performance score
     */
    BigDecimal calculateOverallScore(String employeeId, LocalDate startDate, LocalDate endDate);
    
    /**
     * Get employee performance history
     */
    List<PerformanceReviewDTO> getEmployeePerformanceHistory(String employeeId);
    
    /**
     * Get upcoming reviews
     */
    List<PerformanceReviewDTO> getUpcomingReviews(String reviewerId);
    
    /**
     * Complete performance review
     */
    PerformanceReviewDTO completeReview(String reviewId, Map<String, BigDecimal> scores, String comments);
    
    /**
     * Get training recommendations for employee
     */
    List<TrainingDTO> getTrainingRecommendations(String employeeId);
    
    /**
     * Enroll employee in training
     */
    void enrollInTraining(String employeeId, String trainingId);
    
    /**
     * Update training progress
     */
    void updateTrainingProgress(String employeeId, String trainingId, Double progress);
    
    /**
     * Get department performance summary
     */
    Map<String, Object> getDepartmentPerformanceSummary(String department);
}
