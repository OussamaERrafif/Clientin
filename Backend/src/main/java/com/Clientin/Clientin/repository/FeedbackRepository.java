package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String>, JpaSpecificationExecutor<Feedback> {

    // Automatic query methods
    List<Feedback> findByClientId(String clientId);
    List<Feedback> findByEmployeeId(String employeeId);
    List<Feedback> findByRating(Integer rating);
    List<Feedback> findBySentiment(Feedback.Sentiment sentiment);
    List<Feedback> findBySource(Feedback.Source source);
    
    // Custom JPQL queries
    @Query("SELECT f FROM Feedback f WHERE f.clientId = :clientId")
    List<Feedback> findByClientIdCustom(String clientId);

    @Query("SELECT f FROM Feedback f WHERE f.rating >= :minRating")
    List<Feedback> findByMinimumRating(Integer minRating);

    @Query("SELECT f FROM Feedback f")
    List<Feedback> findAllFeedback();
}