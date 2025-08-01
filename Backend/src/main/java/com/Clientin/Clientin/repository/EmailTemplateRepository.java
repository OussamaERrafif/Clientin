package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.EmailTemplate;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
import java.util.List;
    

    @Repository
    public interface EmailTemplateRepository 
        extends JpaRepository<EmailTemplate, String> {

        // Automatic query methods
        List<EmailTemplate> findByTemplatename(String templateName);
    List<EmailTemplate> findByTemplatetype(EmailTemplate.TemplateType templateType);
    List<EmailTemplate> findBySubject(String subject);
        
        // Custom JPQL queries
        @Query("SELECT e FROM EmailTemplate e WHERE e.templateName = :value")
        List<EmailTemplate> findByTemplatename(String value);

        @Query("SELECT e FROM EmailTemplate e JOIN FETCH e.templateName")
        List<EmailTemplate> findAllWithTemplatename();
        
        // Relationship queries
    
    }