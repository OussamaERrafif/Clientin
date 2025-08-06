package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.NFCSession;
    import org.junit.jupiter.api.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
    import org.springframework.dao.DataIntegrityViolationException;
    import org.springframework.data.domain.*;
    import org.springframework.test.context.jdbc.Sql;
    import jakarta.persistence.EntityManager;
    import java.util.List;
    import java.util.Optional;

    import static org.assertj.core.api.Assertions.*;

    @DataJpaTest
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class NFCSessionRepositoryTest {

        @Autowired
        private NFCSessionRepository repository;
        
        @Autowired
        private EntityManager em;

        private NFCSession testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            NFCDevice device = new NFCDevice();
        em.persist(device);
        Client client = new Client();
        em.persist(client);
        }

        @BeforeEach
        void setUp() {
            testEntity = new NFCSession();
            testEntity.setDeviceId("TEST_DEVICEID");
        testEntity.setSessionToken("TEST_SESSIONTOKEN");
        testEntity.setClientId("TEST_CLIENTID");
        // TODO: Handle SessionStatus type for status
        // testEntity.setStatus(/* unknown type */);
        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        testEntity.setExpiresAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        testEntity.setCompletedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        testEntity.setMetadata("TEST_METADATA");
        NFCDevice device = NFCDeviceTestUtils.createTestNFCDevice();
        em.persist(device);
        testEntity.setDevice(device);
        Client client = ClientTestUtils.createTestClient();
        em.persist(client);
        testEntity.setClient(client);
            repository.saveAndFlush(testEntity);
            em.clear();
        }

        @AfterEach
        void tearDown() {
            NFCDeviceTestUtils.cleanupNFCDevice(em);
        ClientTestUtils.cleanupClient(em);
        }

        @Test
        @Transactional
        void shouldPersistAndRetrieveEntity() {
            List<NFCSession> all = repository.findAll();
            assertThat(all)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("id", "version")
                .isEqualTo(testEntity);
        }

        @Test
        void shouldFindByAllFields() {
    
            // Test deviceId field
            List<NFCSession> byDeviceid = repository.findByDeviceid(testEntity.getDeviceid());
            assertThat(byDeviceid)
                .hasSize(1)
                .extracting("deviceId")
                .containsExactly(testEntity.getDeviceid());

            // Test sessionToken field
            List<NFCSession> bySessiontoken = repository.findBySessiontoken(testEntity.getSessiontoken());
            assertThat(bySessiontoken)
                .hasSize(1)
                .extracting("sessionToken")
                .containsExactly(testEntity.getSessiontoken());

            // Test clientId field
            List<NFCSession> byClientid = repository.findByClientid(testEntity.getClientid());
            assertThat(byClientid)
                .hasSize(1)
                .extracting("clientId")
                .containsExactly(testEntity.getClientid());

            // Test status field
            List<NFCSession> byStatus = repository.findByStatus(testEntity.getStatus());
            assertThat(byStatus)
                .hasSize(1)
                .extracting("status")
                .containsExactly(testEntity.getStatus());

            // Test createdAt field
            List<NFCSession> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test expiresAt field
            List<NFCSession> byExpiresat = repository.findByExpiresat(testEntity.getExpiresat());
            assertThat(byExpiresat)
                .hasSize(1)
                .extracting("expiresAt")
                .containsExactly(testEntity.getExpiresat());

            // Test completedAt field
            List<NFCSession> byCompletedat = repository.findByCompletedat(testEntity.getCompletedat());
            assertThat(byCompletedat)
                .hasSize(1)
                .extracting("completedAt")
                .containsExactly(testEntity.getCompletedat());

            // Test metadata field
            List<NFCSession> byMetadata = repository.findByMetadata(testEntity.getMetadata());
            assertThat(byMetadata)
                .hasSize(1)
                .extracting("metadata")
                .containsExactly(testEntity.getMetadata());

            // Test device field
            List<NFCSession> byDevice = repository.findByDevice(testEntity.getDevice());
            assertThat(byDevice)
                .hasSize(1)
                .extracting("device")
                .containsExactly(testEntity.getDevice());

            // Test client field
            List<NFCSession> byClient = repository.findByClient(testEntity.getClient());
            assertThat(byClient)
                .hasSize(1)
                .extracting("client")
                .containsExactly(testEntity.getClient());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<NFCSession> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setDeviceid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setSessiontoken(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setClientid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setStatus(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setExpiresat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setCompletedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setMetadata(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setDevice(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCSession invalidEntity = createTestEntity();
            invalidEntity.setClient(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<NFCSession> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_nFCSessions.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        @Test
        void getNFCDevice_ShouldReturnRelatedResources() throws Exception {
            List<NFCDeviceDTO> items = List.of(new NFCDeviceDTO());
            given(nFCSessionService.getDevice(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/device", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.NFCDeviceList").exists());
        }

        @Test
        void getClient_ShouldReturnRelatedResources() throws Exception {
            List<ClientDTO> items = List.of(new ClientDTO());
            given(nFCSessionService.getClient(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/client", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.ClientList").exists());
        }

        private NFCSession createTestEntity() {
            NFCSession entity = new NFCSession();
            entity.setDeviceid(testEntity.getDeviceid());
        entity.setSessiontoken(testEntity.getSessiontoken());
        entity.setClientid(testEntity.getClientid());
        entity.setStatus(testEntity.getStatus());
        entity.setCreatedat(testEntity.getCreatedat());
        entity.setExpiresat(testEntity.getExpiresat());
        entity.setCompletedat(testEntity.getCompletedat());
        entity.setMetadata(testEntity.getMetadata());
        entity.setDevice(testEntity.getDevice());
        entity.setClient(testEntity.getClient());
            return entity;
        }
    }
