package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.PerformanceReview;
import com.Clientin.Clientin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface PerformanceReviewRepository 
    extends JpaRepository<PerformanceReview, String> {

    // Automatic query methods
    List<PerformanceReview> findByEmployeeId(String employeeId);
    List<PerformanceReview> findByReviewerId(String reviewerId);
    List<PerformanceReview> findByStatus(PerformanceReview.ReviewStatus status);
    
    // Custom JPQL queries
    @Query("SELECT e FROM PerformanceReview e WHERE e.employeeId = :employeeId ORDER BY e.reviewPeriodEnd DESC")
    List<PerformanceReview> findByEmployeeIdOrderByPeriodDesc(String employeeId);

    @Query("SELECT e FROM PerformanceReview e WHERE e.reviewerId = :reviewerId AND e.status IN :statuses")
    List<PerformanceReview> findByReviewerIdAndStatuses(String reviewerId, List<PerformanceReview.ReviewStatus> statuses);
    
    @Query("SELECT e FROM PerformanceReview e WHERE e.reviewPeriodStart >= :startDate AND e.reviewPeriodEnd <= :endDate")
    List<PerformanceReview> findByReviewPeriod(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT AVG(e.overallScore) FROM PerformanceReview e WHERE e.employeeId = :employeeId AND e.status = 'COMPLETED'")
    Double getAverageScoreByEmployeeId(String employeeId);
}