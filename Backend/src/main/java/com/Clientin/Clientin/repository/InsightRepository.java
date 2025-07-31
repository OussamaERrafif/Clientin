package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Insight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface InsightRepository extends JpaRepository<Insight, String>, JpaSpecificationExecutor<Insight> {

    // Automatic query methods
    List<Insight> findByPeriod(Insight.Period period);
    List<Insight> findByPositiveCount(Long positiveCount);
    List<Insight> findByNegativeCount(Long negativeCount);
    List<Insight> findByTotalFeedbacks(Long totalFeedbacks);
    
    // Custom JPQL queries
    @Query("SELECT i FROM Insight i WHERE i.period = :period")
    List<Insight> findByPeriodCustom(Insight.Period period);

    @Query("SELECT i FROM Insight i WHERE i.totalFeedbacks > :minFeedbacks")
    List<Insight> findByMinimumFeedbacks(Long minFeedbacks);

    @Query("SELECT i FROM Insight i WHERE i.generatedAt BETWEEN :startDate AND :endDate")
    List<Insight> findByGeneratedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}