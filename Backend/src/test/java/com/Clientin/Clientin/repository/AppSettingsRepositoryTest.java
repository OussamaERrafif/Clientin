package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.AppSettings;
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
    class AppSettingsRepositoryTest {

        @Autowired
        private AppSettingsRepository repository;
        
        @Autowired
        private EntityManager em;

        private AppSettings testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            
        }

        @BeforeEach
        void setUp() {
            testEntity = new AppSettings();
            testEntity.setSettingKey("TEST_SETTINGKEY");\n        testEntity.setSettingValue("TEST_SETTINGVALUE");\n        // TODO: Handle SettingType type for settingType\n        // testEntity.setSettingType(/* unknown type */);\n        testEntity.setCategory("TEST_CATEGORY");\n        testEntity.setDescription("Test description for description field");\n        testEntity.setDefaultValue("TEST_DEFAULTVALUE");\n        testEntity.setValidationRules("TEST_VALIDATIONRULES");\n        testEntity.setIsPublic(true);\n        testEntity.setIsEncrypted(true);\n        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setUpdatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
            repository.saveAndFlush(testEntity);
            em.clear();
        }

        @AfterEach
        void tearDown() {
    
        }

        @Test
        @Transactional
        void shouldPersistAndRetrieveEntity() {
            List<AppSettings> all = repository.findAll();
            assertThat(all)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("id", "version")
                .isEqualTo(testEntity);
        }

        @Test
        void shouldFindByAllFields() {
    
            // Test settingKey field
            List<AppSettings> bySettingkey = repository.findBySettingkey(testEntity.getSettingkey());
            assertThat(bySettingkey)
                .hasSize(1)
                .extracting("settingKey")
                .containsExactly(testEntity.getSettingkey());

            // Test settingValue field
            List<AppSettings> bySettingvalue = repository.findBySettingvalue(testEntity.getSettingvalue());
            assertThat(bySettingvalue)
                .hasSize(1)
                .extracting("settingValue")
                .containsExactly(testEntity.getSettingvalue());

            // Test settingType field
            List<AppSettings> bySettingtype = repository.findBySettingtype(testEntity.getSettingtype());
            assertThat(bySettingtype)
                .hasSize(1)
                .extracting("settingType")
                .containsExactly(testEntity.getSettingtype());

            // Test category field
            List<AppSettings> byCategory = repository.findByCategory(testEntity.getCategory());
            assertThat(byCategory)
                .hasSize(1)
                .extracting("category")
                .containsExactly(testEntity.getCategory());

            // Test description field
            List<AppSettings> byDescription = repository.findByDescription(testEntity.getDescription());
            assertThat(byDescription)
                .hasSize(1)
                .extracting("description")
                .containsExactly(testEntity.getDescription());

            // Test defaultValue field
            List<AppSettings> byDefaultvalue = repository.findByDefaultvalue(testEntity.getDefaultvalue());
            assertThat(byDefaultvalue)
                .hasSize(1)
                .extracting("defaultValue")
                .containsExactly(testEntity.getDefaultvalue());

            // Test validationRules field
            List<AppSettings> byValidationrules = repository.findByValidationrules(testEntity.getValidationrules());
            assertThat(byValidationrules)
                .hasSize(1)
                .extracting("validationRules")
                .containsExactly(testEntity.getValidationrules());

            // Test isPublic field
            List<AppSettings> byIspublic = repository.findByIspublic(testEntity.getIspublic());
            assertThat(byIspublic)
                .hasSize(1)
                .extracting("isPublic")
                .containsExactly(testEntity.getIspublic());

            // Test isEncrypted field
            List<AppSettings> byIsencrypted = repository.findByIsencrypted(testEntity.getIsencrypted());
            assertThat(byIsencrypted)
                .hasSize(1)
                .extracting("isEncrypted")
                .containsExactly(testEntity.getIsencrypted());

            // Test createdAt field
            List<AppSettings> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test updatedAt field
            List<AppSettings> byUpdatedat = repository.findByUpdatedat(testEntity.getUpdatedat());
            assertThat(byUpdatedat)
                .hasSize(1)
                .extracting("updatedAt")
                .containsExactly(testEntity.getUpdatedat());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<AppSettings> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setSettingkey(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setSettingvalue(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setSettingtype(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setCategory(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setDescription(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setDefaultvalue(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setValidationrules(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setIspublic(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setIsencrypted(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AppSettings invalidEntity = createTestEntity();
            invalidEntity.setUpdatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<AppSettings> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_appSettingss.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        

        private AppSettings createTestEntity() {
            AppSettings entity = new AppSettings();
            entity.setSettingkey(testEntity.getSettingkey());\n        entity.setSettingvalue(testEntity.getSettingvalue());\n        entity.setSettingtype(testEntity.getSettingtype());\n        entity.setCategory(testEntity.getCategory());\n        entity.setDescription(testEntity.getDescription());\n        entity.setDefaultvalue(testEntity.getDefaultvalue());\n        entity.setValidationrules(testEntity.getValidationrules());\n        entity.setIspublic(testEntity.getIspublic());\n        entity.setIsencrypted(testEntity.getIsencrypted());\n        entity.setCreatedat(testEntity.getCreatedat());\n        entity.setUpdatedat(testEntity.getUpdatedat());
            return entity;
        }
    }