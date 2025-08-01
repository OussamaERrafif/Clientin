package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.Training;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
import java.util.List;
    

    @Repository
    public interface TrainingRepository 
        extends JpaRepository<Training, String> {

        // Automatic query methods
        List<Training> findByTitle(String title);
    List<Training> findByDescription(String description);
    List<Training> findByTrainingtype(Training.TrainingType trainingType);
        
        // Custom JPQL queries
        @Query("SELECT e FROM Training e WHERE e.title = :value")
        List<Training> findByTitle(String value);

        @Query("SELECT e FROM Training e JOIN FETCH e.title")
        List<Training> findAllWithTitle();
        
        // Relationship queries
    
    }