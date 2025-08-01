package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.Report;
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
    class ReportRepositoryTest {

        @Autowired
        private ReportRepository repository;
        
        @Autowired
        private EntityManager em;

        private Report testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            User user = new User();
        em.persist(user);
        }

        @BeforeEach
        void setUp() {
            testEntity = new Report();
            testEntity.setUserId("TEST_USERID");\n        testEntity.setReportName("Sample Reportname");\n        // TODO: Handle ReportType type for reportType\n        // testEntity.setReportType(/* unknown type */);\n        // TODO: Handle ReportStatus type for status\n        // testEntity.setStatus(/* unknown type */);\n        testEntity.setParameters("TEST_PARAMETERS");\n        testEntity.setFilePath("TEST_FILEPATH");\n        testEntity.setFileSize(42L);\n        // TODO: Handle ReportFormat type for format\n        // testEntity.setFormat(/* unknown type */);\n        testEntity.setScheduledFor(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setGeneratedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setExpiresAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setUpdatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        User user = UserTestUtils.createTestUser();
        em.persist(user);
        testEntity.setUser(user);
            repository.saveAndFlush(testEntity);
            em.clear();
        }

        @AfterEach
        void tearDown() {
            UserTestUtils.cleanupUser(em);
        }

        @Test
        @Transactional
        void shouldPersistAndRetrieveEntity() {
            List<Report> all = repository.findAll();
            assertThat(all)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("id", "version")
                .isEqualTo(testEntity);
        }

        @Test
        void shouldFindByAllFields() {
    
            // Test userId field
            List<Report> byUserid = repository.findByUserid(testEntity.getUserid());
            assertThat(byUserid)
                .hasSize(1)
                .extracting("userId")
                .containsExactly(testEntity.getUserid());

            // Test reportName field
            List<Report> byReportname = repository.findByReportname(testEntity.getReportname());
            assertThat(byReportname)
                .hasSize(1)
                .extracting("reportName")
                .containsExactly(testEntity.getReportname());

            // Test reportType field
            List<Report> byReporttype = repository.findByReporttype(testEntity.getReporttype());
            assertThat(byReporttype)
                .hasSize(1)
                .extracting("reportType")
                .containsExactly(testEntity.getReporttype());

            // Test status field
            List<Report> byStatus = repository.findByStatus(testEntity.getStatus());
            assertThat(byStatus)
                .hasSize(1)
                .extracting("status")
                .containsExactly(testEntity.getStatus());

            // Test parameters field
            List<Report> byParameters = repository.findByParameters(testEntity.getParameters());
            assertThat(byParameters)
                .hasSize(1)
                .extracting("parameters")
                .containsExactly(testEntity.getParameters());

            // Test filePath field
            List<Report> byFilepath = repository.findByFilepath(testEntity.getFilepath());
            assertThat(byFilepath)
                .hasSize(1)
                .extracting("filePath")
                .containsExactly(testEntity.getFilepath());

            // Test fileSize field
            List<Report> byFilesize = repository.findByFilesize(testEntity.getFilesize());
            assertThat(byFilesize)
                .hasSize(1)
                .extracting("fileSize")
                .containsExactly(testEntity.getFilesize());

            // Test format field
            List<Report> byFormat = repository.findByFormat(testEntity.getFormat());
            assertThat(byFormat)
                .hasSize(1)
                .extracting("format")
                .containsExactly(testEntity.getFormat());

            // Test scheduledFor field
            List<Report> byScheduledfor = repository.findByScheduledfor(testEntity.getScheduledfor());
            assertThat(byScheduledfor)
                .hasSize(1)
                .extracting("scheduledFor")
                .containsExactly(testEntity.getScheduledfor());

            // Test generatedAt field
            List<Report> byGeneratedat = repository.findByGeneratedat(testEntity.getGeneratedat());
            assertThat(byGeneratedat)
                .hasSize(1)
                .extracting("generatedAt")
                .containsExactly(testEntity.getGeneratedat());

            // Test expiresAt field
            List<Report> byExpiresat = repository.findByExpiresat(testEntity.getExpiresat());
            assertThat(byExpiresat)
                .hasSize(1)
                .extracting("expiresAt")
                .containsExactly(testEntity.getExpiresat());

            // Test createdAt field
            List<Report> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test updatedAt field
            List<Report> byUpdatedat = repository.findByUpdatedat(testEntity.getUpdatedat());
            assertThat(byUpdatedat)
                .hasSize(1)
                .extracting("updatedAt")
                .containsExactly(testEntity.getUpdatedat());

            // Test user field
            List<Report> byUser = repository.findByUser(testEntity.getUser());
            assertThat(byUser)
                .hasSize(1)
                .extracting("user")
                .containsExactly(testEntity.getUser());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<Report> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            Report invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setUserid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setReportname(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setReporttype(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setStatus(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setParameters(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setFilepath(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setFilesize(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setFormat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setScheduledfor(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setGeneratedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setExpiresat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setUpdatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Report invalidEntity = createTestEntity();
            invalidEntity.setUser(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<Report> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_reports.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        
        @Test
        @WithMockUser(authorities = "SCOPE_REPORT_READ")
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(reportService.getUser(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/user", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private Report createTestEntity() {
            Report entity = new Report();
            entity.setUserid(testEntity.getUserid());\n        entity.setReportname(testEntity.getReportname());\n        entity.setReporttype(testEntity.getReporttype());\n        entity.setStatus(testEntity.getStatus());\n        entity.setParameters(testEntity.getParameters());\n        entity.setFilepath(testEntity.getFilepath());\n        entity.setFilesize(testEntity.getFilesize());\n        entity.setFormat(testEntity.getFormat());\n        entity.setScheduledfor(testEntity.getScheduledfor());\n        entity.setGeneratedat(testEntity.getGeneratedat());\n        entity.setExpiresat(testEntity.getExpiresat());\n        entity.setCreatedat(testEntity.getCreatedat());\n        entity.setUpdatedat(testEntity.getUpdatedat());\n        entity.setUser(testEntity.getUser());
            return entity;
        }
    }