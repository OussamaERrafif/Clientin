package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.EmailTemplate;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;
import java.util.List;

    @Repository
    public interface EmailTemplateRepository 
        extends JpaRepository<EmailTemplate, String>, JpaSpecificationExecutor<EmailTemplate> {

        // Automatic query methods
        List<EmailTemplate> findByTemplateName(String templateName);
        List<EmailTemplate> findByTemplateType(EmailTemplate.TemplateType templateType);
        List<EmailTemplate> findBySubject(String subject);
        
        // Custom JPQL queries
        @Query("SELECT e FROM EmailTemplate e WHERE e.templateName = :value")
        List<EmailTemplate> findByTemplateNameCustom(String value);

        @Query("SELECT e FROM EmailTemplate e JOIN FETCH e.templateName")
        List<EmailTemplate> findAllWithTemplateName();
        
        // Relationship queries
    
    }