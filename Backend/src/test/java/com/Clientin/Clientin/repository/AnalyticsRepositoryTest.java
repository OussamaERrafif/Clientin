package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.Analytics;
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
    class AnalyticsRepositoryTest {

        @Autowired
        private AnalyticsRepository repository;
        
        @Autowired
        private EntityManager em;

        private Analytics testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            User employee = new User();
        em.persist(employee);
        }

        @BeforeEach
        void setUp() {
            testEntity = new Analytics();
            // TODO: Handle MetricType type for metricType\n        // testEntity.setMetricType(/* unknown type */);\n        testEntity.setMetricKey("TEST_METRICKEY");\n        testEntity.setMetricValue(new BigDecimal("1234.56"));\n        testEntity.setDateKey(LocalDate.of(2024, 1, 1));\n        testEntity.setHourKey(42);\n        testEntity.setEmployeeId("TEST_EMPLOYEEID");\n        testEntity.setDepartment("TEST_DEPARTMENT");\n        testEntity.setMetadata("TEST_METADATA");\n        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        User employee = UserTestUtils.createTestUser();
        em.persist(employee);
        testEntity.setEmployee(employee);
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
            List<Analytics> all = repository.findAll();
            assertThat(all)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("id", "version")
                .isEqualTo(testEntity);
        }

        @Test
        void shouldFindByAllFields() {
    
            // Test metricType field
            List<Analytics> byMetrictype = repository.findByMetrictype(testEntity.getMetrictype());
            assertThat(byMetrictype)
                .hasSize(1)
                .extracting("metricType")
                .containsExactly(testEntity.getMetrictype());

            // Test metricKey field
            List<Analytics> byMetrickey = repository.findByMetrickey(testEntity.getMetrickey());
            assertThat(byMetrickey)
                .hasSize(1)
                .extracting("metricKey")
                .containsExactly(testEntity.getMetrickey());

            // Test metricValue field
            List<Analytics> byMetricvalue = repository.findByMetricvalue(testEntity.getMetricvalue());
            assertThat(byMetricvalue)
                .hasSize(1)
                .extracting("metricValue")
                .containsExactly(testEntity.getMetricvalue());

            // Test dateKey field
            List<Analytics> byDatekey = repository.findByDatekey(testEntity.getDatekey());
            assertThat(byDatekey)
                .hasSize(1)
                .extracting("dateKey")
                .containsExactly(testEntity.getDatekey());

            // Test hourKey field
            List<Analytics> byHourkey = repository.findByHourkey(testEntity.getHourkey());
            assertThat(byHourkey)
                .hasSize(1)
                .extracting("hourKey")
                .containsExactly(testEntity.getHourkey());

            // Test employeeId field
            List<Analytics> byEmployeeid = repository.findByEmployeeid(testEntity.getEmployeeid());
            assertThat(byEmployeeid)
                .hasSize(1)
                .extracting("employeeId")
                .containsExactly(testEntity.getEmployeeid());

            // Test department field
            List<Analytics> byDepartment = repository.findByDepartment(testEntity.getDepartment());
            assertThat(byDepartment)
                .hasSize(1)
                .extracting("department")
                .containsExactly(testEntity.getDepartment());

            // Test metadata field
            List<Analytics> byMetadata = repository.findByMetadata(testEntity.getMetadata());
            assertThat(byMetadata)
                .hasSize(1)
                .extracting("metadata")
                .containsExactly(testEntity.getMetadata());

            // Test createdAt field
            List<Analytics> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test employee field
            List<Analytics> byEmployee = repository.findByEmployee(testEntity.getEmployee());
            assertThat(byEmployee)
                .hasSize(1)
                .extracting("employee")
                .containsExactly(testEntity.getEmployee());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<Analytics> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            Analytics invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setMetrictype(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setMetrickey(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setMetricvalue(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setDatekey(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setHourkey(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setEmployeeid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setDepartment(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setMetadata(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Analytics invalidEntity = createTestEntity();
            invalidEntity.setEmployee(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<Analytics> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_analyticss.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        
        @Test
        @WithMockUser(authorities = "SCOPE_ANALYTICS_READ")
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(analyticsService.getEmployee(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/employee", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private Analytics createTestEntity() {
            Analytics entity = new Analytics();
            entity.setMetrictype(testEntity.getMetrictype());\n        entity.setMetrickey(testEntity.getMetrickey());\n        entity.setMetricvalue(testEntity.getMetricvalue());\n        entity.setDatekey(testEntity.getDatekey());\n        entity.setHourkey(testEntity.getHourkey());\n        entity.setEmployeeid(testEntity.getEmployeeid());\n        entity.setDepartment(testEntity.getDepartment());\n        entity.setMetadata(testEntity.getMetadata());\n        entity.setCreatedat(testEntity.getCreatedat());\n        entity.setEmployee(testEntity.getEmployee());
            return entity;
        }
    }