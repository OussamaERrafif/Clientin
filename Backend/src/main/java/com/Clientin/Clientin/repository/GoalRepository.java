package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface GoalRepository extends JpaRepository<Goal, String>, JpaSpecificationExecutor<Goal> {

    // Automatic query methods
    List<Goal> findByTitle(String title);
    List<Goal> findByDescription(String description);
    List<Goal> findByProgress(Double progress);
    List<Goal> findByEmployeeProfileUserId(String employeeProfileUserId);
    List<Goal> findByDueDate(LocalDate dueDate);
    
    // Custom JPQL queries
    @Query("SELECT g FROM Goal g WHERE g.title = :title")
    List<Goal> findByTitleCustom(String title);

    @Query("SELECT g FROM Goal g WHERE g.progress >= :minProgress")
    List<Goal> findByMinimumProgress(Double minProgress);

    @Query("SELECT g FROM Goal g WHERE g.dueDate BETWEEN :startDate AND :endDate")
    List<Goal> findByDueDateBetween(LocalDate startDate, LocalDate endDate);
}