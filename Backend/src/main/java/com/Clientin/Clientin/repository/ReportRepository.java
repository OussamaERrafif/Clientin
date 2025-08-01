package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Report;
import com.Clientin.Clientin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface ReportRepository 
    extends JpaRepository<Report, String> {

    // Automatic query methods
    List<Report> findByUserId(String userId);
    List<Report> findByReportType(Report.ReportType reportType);
    List<Report> findByStatus(Report.ReportStatus status);
    List<Report> findByFormat(Report.ReportFormat format);
    
    // Custom JPQL queries
    @Query("SELECT e FROM Report e WHERE e.userId = :userId ORDER BY e.createdAt DESC")
    List<Report> findByUserIdOrderByCreatedAtDesc(String userId);

    @Query("SELECT e FROM Report e WHERE e.status = 'PENDING' AND e.scheduledFor <= :now")
    List<Report> findDueReports(LocalDateTime now);
    
    @Query("SELECT e FROM Report e WHERE e.expiresAt < :now")
    List<Report> findExpiredReports(LocalDateTime now);
    
    @Query("SELECT e FROM Report e WHERE e.status = 'COMPLETED' AND e.generatedAt BETWEEN :startDate AND :endDate")
    List<Report> findCompletedReportsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}