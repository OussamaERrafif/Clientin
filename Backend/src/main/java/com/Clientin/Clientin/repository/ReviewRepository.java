package com.Clientin.Clientin.repository;

import com.Clientin.Clientin.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ReviewRepository 
    extends JpaRepository<Review, String>, JpaSpecificationExecutor<Review> {

    // Add custom query methods here
    
}