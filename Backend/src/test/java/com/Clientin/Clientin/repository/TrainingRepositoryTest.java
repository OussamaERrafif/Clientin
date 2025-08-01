package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.Training;
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
    class TrainingRepositoryTest {

        @Autowired
        private TrainingRepository repository;
        
        @Autowired
        private EntityManager em;

        private Training testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            
        }

        @BeforeEach
        void setUp() {
            testEntity = new Training();
            testEntity.setTitle("TEST_TITLE");\n        testEntity.setDescription("Test description for description field");\n        // TODO: Handle TrainingType type for trainingType\n        // testEntity.setTrainingType(/* unknown type */);\n        testEntity.setDurationHours(42);\n        testEntity.setContentUrl("TEST_CONTENTURL");\n        testEntity.setPrerequisites("TEST_PREREQUISITES");\n        testEntity.setLearningObjectives("TEST_LEARNINGOBJECTIVES");\n        // TODO: Handle TrainingStatus type for status\n        // testEntity.setStatus(/* unknown type */);\n        testEntity.setMaxParticipants(42);\n        testEntity.setPassingScore(42);\n        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setUpdatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
            repository.saveAndFlush(testEntity);
            em.clear();
        }

        @AfterEach
        void tearDown() {
    
        }

        @Test
        @Transactional
        void shouldPersistAndRetrieveEntity() {
            List<Training> all = repository.findAll();
            assertThat(all)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("id", "version")
                .isEqualTo(testEntity);
        }

        @Test
        void shouldFindByAllFields() {
    
            // Test title field
            List<Training> byTitle = repository.findByTitle(testEntity.getTitle());
            assertThat(byTitle)
                .hasSize(1)
                .extracting("title")
                .containsExactly(testEntity.getTitle());

            // Test description field
            List<Training> byDescription = repository.findByDescription(testEntity.getDescription());
            assertThat(byDescription)
                .hasSize(1)
                .extracting("description")
                .containsExactly(testEntity.getDescription());

            // Test trainingType field
            List<Training> byTrainingtype = repository.findByTrainingtype(testEntity.getTrainingtype());
            assertThat(byTrainingtype)
                .hasSize(1)
                .extracting("trainingType")
                .containsExactly(testEntity.getTrainingtype());

            // Test durationHours field
            List<Training> byDurationhours = repository.findByDurationhours(testEntity.getDurationhours());
            assertThat(byDurationhours)
                .hasSize(1)
                .extracting("durationHours")
                .containsExactly(testEntity.getDurationhours());

            // Test contentUrl field
            List<Training> byContenturl = repository.findByContenturl(testEntity.getContenturl());
            assertThat(byContenturl)
                .hasSize(1)
                .extracting("contentUrl")
                .containsExactly(testEntity.getContenturl());

            // Test prerequisites field
            List<Training> byPrerequisites = repository.findByPrerequisites(testEntity.getPrerequisites());
            assertThat(byPrerequisites)
                .hasSize(1)
                .extracting("prerequisites")
                .containsExactly(testEntity.getPrerequisites());

            // Test learningObjectives field
            List<Training> byLearningobjectives = repository.findByLearningobjectives(testEntity.getLearningobjectives());
            assertThat(byLearningobjectives)
                .hasSize(1)
                .extracting("learningObjectives")
                .containsExactly(testEntity.getLearningobjectives());

            // Test status field
            List<Training> byStatus = repository.findByStatus(testEntity.getStatus());
            assertThat(byStatus)
                .hasSize(1)
                .extracting("status")
                .containsExactly(testEntity.getStatus());

            // Test maxParticipants field
            List<Training> byMaxparticipants = repository.findByMaxparticipants(testEntity.getMaxparticipants());
            assertThat(byMaxparticipants)
                .hasSize(1)
                .extracting("maxParticipants")
                .containsExactly(testEntity.getMaxparticipants());

            // Test passingScore field
            List<Training> byPassingscore = repository.findByPassingscore(testEntity.getPassingscore());
            assertThat(byPassingscore)
                .hasSize(1)
                .extracting("passingScore")
                .containsExactly(testEntity.getPassingscore());

            // Test createdAt field
            List<Training> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test updatedAt field
            List<Training> byUpdatedat = repository.findByUpdatedat(testEntity.getUpdatedat());
            assertThat(byUpdatedat)
                .hasSize(1)
                .extracting("updatedAt")
                .containsExactly(testEntity.getUpdatedat());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<Training> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            Training invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setTitle(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setDescription(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setTrainingtype(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setDurationhours(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setContenturl(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setPrerequisites(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setLearningobjectives(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setStatus(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setMaxparticipants(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setPassingscore(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Training invalidEntity = createTestEntity();
            invalidEntity.setUpdatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<Training> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_trainings.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        

        private Training createTestEntity() {
            Training entity = new Training();
            entity.setTitle(testEntity.getTitle());\n        entity.setDescription(testEntity.getDescription());\n        entity.setTrainingtype(testEntity.getTrainingtype());\n        entity.setDurationhours(testEntity.getDurationhours());\n        entity.setContenturl(testEntity.getContenturl());\n        entity.setPrerequisites(testEntity.getPrerequisites());\n        entity.setLearningobjectives(testEntity.getLearningobjectives());\n        entity.setStatus(testEntity.getStatus());\n        entity.setMaxparticipants(testEntity.getMaxparticipants());\n        entity.setPassingscore(testEntity.getPassingscore());\n        entity.setCreatedat(testEntity.getCreatedat());\n        entity.setUpdatedat(testEntity.getUpdatedat());
            return entity;
        }
    }