package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String>, JpaSpecificationExecutor<Subscription> {

    // Automatic query methods
    List<Subscription> findByUserId(String userId);
    List<Subscription> findByPlan(Subscription.Plan plan);
    List<Subscription> findByStatus(Subscription.Status status);
    List<Subscription> findByStartDate(LocalDate startDate);
    List<Subscription> findByEndDate(LocalDate endDate);
    
    // Custom JPQL queries
    @Query("SELECT s FROM Subscription s WHERE s.userId = :userId")
    List<Subscription> findByUserIdCustom(String userId);

    @Query("SELECT s FROM Subscription s WHERE s.status = 'ACTIVE' AND s.endDate > CURRENT_DATE")
    List<Subscription> findActiveSubscriptions();

    @Query("SELECT s FROM Subscription s WHERE s.endDate BETWEEN :startDate AND :endDate")
    List<Subscription> findExpiringSubscriptions(LocalDate startDate, LocalDate endDate);
}