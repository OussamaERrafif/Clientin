package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.AuditLog;
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
    class AuditLogRepositoryTest {

        @Autowired
        private AuditLogRepository repository;
        
        @Autowired
        private EntityManager em;

        private AuditLog testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            User user = new User();
        em.persist(user);
        }

        @BeforeEach
        void setUp() {
            testEntity = new AuditLog();
            testEntity.setUserId("TEST_USERID");\n        testEntity.setEntityType("TEST_ENTITYTYPE");\n        testEntity.setEntityId("TEST_ENTITYID");\n        // TODO: Handle Action type for action\n        // testEntity.setAction(/* unknown type */);\n        testEntity.setOldValues("TEST_OLDVALUES");\n        testEntity.setNewValues("TEST_NEWVALUES");\n        testEntity.setIpAddress("TEST_IPADDRESS");\n        testEntity.setUserAgent("TEST_USERAGENT");\n        testEntity.setSessionId("TEST_SESSIONID");\n        testEntity.setRequestId("TEST_REQUESTID");\n        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        User user = UserTestUtils.createTestUser();
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
            List<AuditLog> all = repository.findAll();
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
            List<AuditLog> byUserid = repository.findByUserid(testEntity.getUserid());
            assertThat(byUserid)
                .hasSize(1)
                .extracting("userId")
                .containsExactly(testEntity.getUserid());

            // Test entityType field
            List<AuditLog> byEntitytype = repository.findByEntitytype(testEntity.getEntitytype());
            assertThat(byEntitytype)
                .hasSize(1)
                .extracting("entityType")
                .containsExactly(testEntity.getEntitytype());

            // Test entityId field
            List<AuditLog> byEntityid = repository.findByEntityid(testEntity.getEntityid());
            assertThat(byEntityid)
                .hasSize(1)
                .extracting("entityId")
                .containsExactly(testEntity.getEntityid());

            // Test action field
            List<AuditLog> byAction = repository.findByAction(testEntity.getAction());
            assertThat(byAction)
                .hasSize(1)
                .extracting("action")
                .containsExactly(testEntity.getAction());

            // Test oldValues field
            List<AuditLog> byOldvalues = repository.findByOldvalues(testEntity.getOldvalues());
            assertThat(byOldvalues)
                .hasSize(1)
                .extracting("oldValues")
                .containsExactly(testEntity.getOldvalues());

            // Test newValues field
            List<AuditLog> byNewvalues = repository.findByNewvalues(testEntity.getNewvalues());
            assertThat(byNewvalues)
                .hasSize(1)
                .extracting("newValues")
                .containsExactly(testEntity.getNewvalues());

            // Test ipAddress field
            List<AuditLog> byIpaddress = repository.findByIpaddress(testEntity.getIpaddress());
            assertThat(byIpaddress)
                .hasSize(1)
                .extracting("ipAddress")
                .containsExactly(testEntity.getIpaddress());

            // Test userAgent field
            List<AuditLog> byUseragent = repository.findByUseragent(testEntity.getUseragent());
            assertThat(byUseragent)
                .hasSize(1)
                .extracting("userAgent")
                .containsExactly(testEntity.getUseragent());

            // Test sessionId field
            List<AuditLog> bySessionid = repository.findBySessionid(testEntity.getSessionid());
            assertThat(bySessionid)
                .hasSize(1)
                .extracting("sessionId")
                .containsExactly(testEntity.getSessionid());

            // Test requestId field
            List<AuditLog> byRequestid = repository.findByRequestid(testEntity.getRequestid());
            assertThat(byRequestid)
                .hasSize(1)
                .extracting("requestId")
                .containsExactly(testEntity.getRequestid());

            // Test createdAt field
            List<AuditLog> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test user field
            List<AuditLog> byUser = repository.findByUser(testEntity.getUser());
            assertThat(byUser)
                .hasSize(1)
                .extracting("user")
                .containsExactly(testEntity.getUser());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<AuditLog> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setUserid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setEntitytype(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setEntityid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setAction(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setOldvalues(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setNewvalues(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setIpaddress(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setUseragent(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setSessionid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setRequestid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            AuditLog invalidEntity = createTestEntity();
            invalidEntity.setUser(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<AuditLog> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_auditLogs.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        
        @Test
        @WithMockUser(authorities = "SCOPE_AUDITLOG_READ")
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(auditLogService.getUser(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/user", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private AuditLog createTestEntity() {
            AuditLog entity = new AuditLog();
            entity.setUserid(testEntity.getUserid());\n        entity.setEntitytype(testEntity.getEntitytype());\n        entity.setEntityid(testEntity.getEntityid());\n        entity.setAction(testEntity.getAction());\n        entity.setOldvalues(testEntity.getOldvalues());\n        entity.setNewvalues(testEntity.getNewvalues());\n        entity.setIpaddress(testEntity.getIpaddress());\n        entity.setUseragent(testEntity.getUseragent());\n        entity.setSessionid(testEntity.getSessionid());\n        entity.setRequestid(testEntity.getRequestid());\n        entity.setCreatedat(testEntity.getCreatedat());\n        entity.setUser(testEntity.getUser());
            return entity;
        }
    }