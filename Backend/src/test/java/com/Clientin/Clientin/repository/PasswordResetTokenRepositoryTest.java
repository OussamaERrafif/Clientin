package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.PasswordResetToken;
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
    class PasswordResetTokenRepositoryTest {

        @Autowired
        private PasswordResetTokenRepository repository;
        
        @Autowired
        private EntityManager em;

        private PasswordResetToken testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            User user = new User();
        em.persist(user);
        }

        @BeforeEach
        void setUp() {
            testEntity = new PasswordResetToken();
            testEntity.setUserId("TEST_USERID");\n        testEntity.setTokenHash("TEST_TOKENHASH");\n        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setExpiresAt(LocalDateTime.of(2024, 1, 1, 12, 0));\n        testEntity.setUsed(true);\n        testEntity.setIpAddress("TEST_IPADDRESS");\n        User user = UserTestUtils.createTestUser();
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
            List<PasswordResetToken> all = repository.findAll();
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
            List<PasswordResetToken> byUserid = repository.findByUserid(testEntity.getUserid());
            assertThat(byUserid)
                .hasSize(1)
                .extracting("userId")
                .containsExactly(testEntity.getUserid());

            // Test tokenHash field
            List<PasswordResetToken> byTokenhash = repository.findByTokenhash(testEntity.getTokenhash());
            assertThat(byTokenhash)
                .hasSize(1)
                .extracting("tokenHash")
                .containsExactly(testEntity.getTokenhash());

            // Test createdAt field
            List<PasswordResetToken> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test expiresAt field
            List<PasswordResetToken> byExpiresat = repository.findByExpiresat(testEntity.getExpiresat());
            assertThat(byExpiresat)
                .hasSize(1)
                .extracting("expiresAt")
                .containsExactly(testEntity.getExpiresat());

            // Test used field
            List<PasswordResetToken> byUsed = repository.findByUsed(testEntity.getUsed());
            assertThat(byUsed)
                .hasSize(1)
                .extracting("used")
                .containsExactly(testEntity.getUsed());

            // Test ipAddress field
            List<PasswordResetToken> byIpaddress = repository.findByIpaddress(testEntity.getIpaddress());
            assertThat(byIpaddress)
                .hasSize(1)
                .extracting("ipAddress")
                .containsExactly(testEntity.getIpaddress());

            // Test user field
            List<PasswordResetToken> byUser = repository.findByUser(testEntity.getUser());
            assertThat(byUser)
                .hasSize(1)
                .extracting("user")
                .containsExactly(testEntity.getUser());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<PasswordResetToken> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            PasswordResetToken invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PasswordResetToken invalidEntity = createTestEntity();
            invalidEntity.setUserid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PasswordResetToken invalidEntity = createTestEntity();
            invalidEntity.setTokenhash(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PasswordResetToken invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PasswordResetToken invalidEntity = createTestEntity();
            invalidEntity.setExpiresat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PasswordResetToken invalidEntity = createTestEntity();
            invalidEntity.setUsed(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PasswordResetToken invalidEntity = createTestEntity();
            invalidEntity.setIpaddress(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PasswordResetToken invalidEntity = createTestEntity();
            invalidEntity.setUser(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<PasswordResetToken> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_passwordResetTokens.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        
        @Test
        @WithMockUser(authorities = "SCOPE_PASSWORDRESETTOKEN_READ")
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(passwordResetTokenService.getUser(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/user", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private PasswordResetToken createTestEntity() {
            PasswordResetToken entity = new PasswordResetToken();
            entity.setUserid(testEntity.getUserid());\n        entity.setTokenhash(testEntity.getTokenhash());\n        entity.setCreatedat(testEntity.getCreatedat());\n        entity.setExpiresat(testEntity.getExpiresat());\n        entity.setUsed(testEntity.getUsed());\n        entity.setIpaddress(testEntity.getIpaddress());\n        entity.setUser(testEntity.getUser());
            return entity;
        }
    }