package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Analytics;
import com.Clientin.Clientin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface AnalyticsRepository 
    extends JpaRepository<Analytics, String>, JpaSpecificationExecutor<Analytics> {

    // Automatic query methods
    List<Analytics> findByMetricType(Analytics.MetricType metricType);
    List<Analytics> findByMetricKey(String metricKey);
    List<Analytics> findByEmployeeId(String employeeId);
    List<Analytics> findByDateKey(LocalDate dateKey);
    List<Analytics> findByDepartment(String department);
    
    // Custom JPQL queries
    @Query("SELECT e FROM Analytics e WHERE e.metricType = :metricType AND e.dateKey BETWEEN :startDate AND :endDate")
    List<Analytics> findByMetricTypeAndDateRange(Analytics.MetricType metricType, LocalDate startDate, LocalDate endDate);

    @Query("SELECT e FROM Analytics e WHERE e.employeeId = :employeeId AND e.dateKey BETWEEN :startDate AND :endDate")
    List<Analytics> findByEmployeeIdAndDateRange(String employeeId, LocalDate startDate, LocalDate endDate);
    
    // Aggregation queries
    @Query("SELECT AVG(e.metricValue) FROM Analytics e WHERE e.metricType = :metricType AND e.dateKey BETWEEN :startDate AND :endDate")
    BigDecimal getAverageMetricValue(Analytics.MetricType metricType, LocalDate startDate, LocalDate endDate);
}