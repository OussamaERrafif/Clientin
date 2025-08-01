package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.Notification;
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
    class NotificationRepositoryTest {

        @Autowired
        private NotificationRepository repository;
        
        @Autowired
        private EntityManager em;

        private Notification testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            User user = new User();
        em.persist(user);
        }

        @BeforeEach
        void setUp() {
            testEntity = new Notification();
            testEntity.setUserId("TEST_USERID");\n        testEntity.setTitle("TEST_TITLE");\n        testEntity.setMessage("TEST_MESSAGE");\n        // TODO: Handle NotificationType type for type\n        // testEntity.setType(/* unknown type */);\n        // TODO: Handle Priority type for priority\n        // testEntity.setPriority(/* unknown type */);\n        testEntity.setReadStatus(true);\n        testEntity.setReadAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setActionUrl("TEST_ACTIONURL");\n        testEntity.setMetadata("TEST_METADATA");\n        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setExpiresAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        User user = UserTestUtils.createTestUser();
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
            List<Notification> all = repository.findAll();
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
            List<Notification> byUserid = repository.findByUserid(testEntity.getUserid());
            assertThat(byUserid)
                .hasSize(1)
                .extracting("userId")
                .containsExactly(testEntity.getUserid());

            // Test title field
            List<Notification> byTitle = repository.findByTitle(testEntity.getTitle());
            assertThat(byTitle)
                .hasSize(1)
                .extracting("title")
                .containsExactly(testEntity.getTitle());

            // Test message field
            List<Notification> byMessage = repository.findByMessage(testEntity.getMessage());
            assertThat(byMessage)
                .hasSize(1)
                .extracting("message")
                .containsExactly(testEntity.getMessage());

            // Test type field
            List<Notification> byType = repository.findByType(testEntity.getType());
            assertThat(byType)
                .hasSize(1)
                .extracting("type")
                .containsExactly(testEntity.getType());

            // Test priority field
            List<Notification> byPriority = repository.findByPriority(testEntity.getPriority());
            assertThat(byPriority)
                .hasSize(1)
                .extracting("priority")
                .containsExactly(testEntity.getPriority());

            // Test readStatus field
            List<Notification> byReadstatus = repository.findByReadstatus(testEntity.getReadstatus());
            assertThat(byReadstatus)
                .hasSize(1)
                .extracting("readStatus")
                .containsExactly(testEntity.getReadstatus());

            // Test readAt field
            List<Notification> byReadat = repository.findByReadat(testEntity.getReadat());
            assertThat(byReadat)
                .hasSize(1)
                .extracting("readAt")
                .containsExactly(testEntity.getReadat());

            // Test actionUrl field
            List<Notification> byActionurl = repository.findByActionurl(testEntity.getActionurl());
            assertThat(byActionurl)
                .hasSize(1)
                .extracting("actionUrl")
                .containsExactly(testEntity.getActionurl());

            // Test metadata field
            List<Notification> byMetadata = repository.findByMetadata(testEntity.getMetadata());
            assertThat(byMetadata)
                .hasSize(1)
                .extracting("metadata")
                .containsExactly(testEntity.getMetadata());

            // Test createdAt field
            List<Notification> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test expiresAt field
            List<Notification> byExpiresat = repository.findByExpiresat(testEntity.getExpiresat());
            assertThat(byExpiresat)
                .hasSize(1)
                .extracting("expiresAt")
                .containsExactly(testEntity.getExpiresat());

            // Test user field
            List<Notification> byUser = repository.findByUser(testEntity.getUser());
            assertThat(byUser)
                .hasSize(1)
                .extracting("user")
                .containsExactly(testEntity.getUser());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<Notification> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            Notification invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setUserid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setTitle(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setMessage(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setType(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setPriority(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setReadstatus(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setReadat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setActionurl(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setMetadata(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setExpiresat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            Notification invalidEntity = createTestEntity();
            invalidEntity.setUser(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<Notification> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_notifications.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        
        @Test
        @WithMockUser(authorities = "SCOPE_NOTIFICATION_READ")
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(notificationService.getUser(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/user", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private Notification createTestEntity() {
            Notification entity = new Notification();
            entity.setUserid(testEntity.getUserid());\n        entity.setTitle(testEntity.getTitle());\n        entity.setMessage(testEntity.getMessage());\n        entity.setType(testEntity.getType());\n        entity.setPriority(testEntity.getPriority());\n        entity.setReadstatus(testEntity.getReadstatus());\n        entity.setReadat(testEntity.getReadat());\n        entity.setActionurl(testEntity.getActionurl());\n        entity.setMetadata(testEntity.getMetadata());\n        entity.setCreatedat(testEntity.getCreatedat());\n        entity.setExpiresat(testEntity.getExpiresat());\n        entity.setUser(testEntity.getUser());
            return entity;
        }
    }