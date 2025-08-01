package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.EmailTemplate;
    import org.junit.jupiter.api.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
    import org.springframework.dao.DataIntegrityViolationException;
    import org.springframework.data.domain.*;
    import org.springframework.test.context.jdbc.Sql;
    import javax.persistence.EntityManager;
    import java.util.List;
    import java.util.Optional;

    import static org.assertj.core.api.Assertions.*;

    @DataJpaTest
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class EmailTemplateRepositoryTest {

        @Autowired
        private EmailTemplateRepository repository;
        
        @Autowired
        private EntityManager em;

        private EmailTemplate testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            
        }

        @BeforeEach
        void setUp() {
            testEntity = new EmailTemplate();
            testEntity.setTemplateName("Sample Templatename");\n        // TODO: Handle TemplateType type for templateType\n        // testEntity.setTemplateType(/* unknown type */);\n        testEntity.setSubject("TEST_SUBJECT");\n        testEntity.setHtmlContent("TEST_HTMLCONTENT");\n        testEntity.setTextContent("TEST_TEXTCONTENT");\n        testEntity.setVariables("TEST_VARIABLES");\n        testEntity.setActive(true);\n        testEntity.setVersion(42);\n        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setUpdatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
            repository.saveAndFlush(testEntity);
            em.clear();
        }

        @AfterEach
        void tearDown() {
    
        }

        @Test
        @Transactional
        void shouldPersistAndRetrieveEntity() {
            List<EmailTemplate> all = repository.findAll();
            assertThat(all)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("id", "version")
                .isEqualTo(testEntity);
        }

        @Test
        void shouldFindByAllFields() {
    
            // Test templateName field
            List<EmailTemplate> byTemplatename = repository.findByTemplatename(testEntity.getTemplatename());
            assertThat(byTemplatename)
                .hasSize(1)
                .extracting("templateName")
                .containsExactly(testEntity.getTemplatename());

            // Test templateType field
            List<EmailTemplate> byTemplatetype = repository.findByTemplatetype(testEntity.getTemplatetype());
            assertThat(byTemplatetype)
                .hasSize(1)
                .extracting("templateType")
                .containsExactly(testEntity.getTemplatetype());

            // Test subject field
            List<EmailTemplate> bySubject = repository.findBySubject(testEntity.getSubject());
            assertThat(bySubject)
                .hasSize(1)
                .extracting("subject")
                .containsExactly(testEntity.getSubject());

            // Test htmlContent field
            List<EmailTemplate> byHtmlcontent = repository.findByHtmlcontent(testEntity.getHtmlcontent());
            assertThat(byHtmlcontent)
                .hasSize(1)
                .extracting("htmlContent")
                .containsExactly(testEntity.getHtmlcontent());

            // Test textContent field
            List<EmailTemplate> byTextcontent = repository.findByTextcontent(testEntity.getTextcontent());
            assertThat(byTextcontent)
                .hasSize(1)
                .extracting("textContent")
                .containsExactly(testEntity.getTextcontent());

            // Test variables field
            List<EmailTemplate> byVariables = repository.findByVariables(testEntity.getVariables());
            assertThat(byVariables)
                .hasSize(1)
                .extracting("variables")
                .containsExactly(testEntity.getVariables());

            // Test active field
            List<EmailTemplate> byActive = repository.findByActive(testEntity.getActive());
            assertThat(byActive)
                .hasSize(1)
                .extracting("active")
                .containsExactly(testEntity.getActive());

            // Test version field
            List<EmailTemplate> byVersion = repository.findByVersion(testEntity.getVersion());
            assertThat(byVersion)
                .hasSize(1)
                .extracting("version")
                .containsExactly(testEntity.getVersion());

            // Test createdAt field
            List<EmailTemplate> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test updatedAt field
            List<EmailTemplate> byUpdatedat = repository.findByUpdatedat(testEntity.getUpdatedat());
            assertThat(byUpdatedat)
                .hasSize(1)
                .extracting("updatedAt")
                .containsExactly(testEntity.getUpdatedat());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<EmailTemplate> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setTemplatename(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setTemplatetype(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setSubject(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setHtmlcontent(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setTextcontent(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setVariables(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setActive(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setVersion(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            EmailTemplate invalidEntity = createTestEntity();
            invalidEntity.setUpdatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<EmailTemplate> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_emailTemplates.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        

        private EmailTemplate createTestEntity() {
            EmailTemplate entity = new EmailTemplate();
            entity.setTemplatename(testEntity.getTemplatename());\n        entity.setTemplatetype(testEntity.getTemplatetype());\n        entity.setSubject(testEntity.getSubject());\n        entity.setHtmlcontent(testEntity.getHtmlcontent());\n        entity.setTextcontent(testEntity.getTextcontent());\n        entity.setVariables(testEntity.getVariables());\n        entity.setActive(testEntity.getActive());\n        entity.setVersion(testEntity.getVersion());\n        entity.setCreatedat(testEntity.getCreatedat());\n        entity.setUpdatedat(testEntity.getUpdatedat());
            return entity;
        }
    }