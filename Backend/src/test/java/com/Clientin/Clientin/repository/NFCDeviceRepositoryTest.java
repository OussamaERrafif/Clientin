package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.NFCDevice;
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
    class NFCDeviceRepositoryTest {

        @Autowired
        private NFCDeviceRepository repository;
        
        @Autowired
        private EntityManager em;

        private NFCDevice testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            
        }

        @BeforeEach
        void setUp() {
            testEntity = new NFCDevice();
            testEntity.setDeviceName("Sample Devicename");
        testEntity.setDeviceSerial("TEST_DEVICESERIAL");
        testEntity.setLocation("TEST_LOCATION");
        // TODO: Handle DeviceStatus type for status
        // testEntity.setStatus(/* unknown type */);
        testEntity.setFirmwareVersion("TEST_FIRMWAREVERSION");
        testEntity.setBatteryLevel(42);
        testEntity.setLastPing(LocalDateTime.of(2024, 1, 1, 12, 0));
        testEntity.setConfiguration("TEST_CONFIGURATION");
        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        testEntity.setUpdatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
            repository.saveAndFlush(testEntity);
            em.clear();
        }

        @AfterEach
        void tearDown() {
    
        }

        @Test
        @Transactional
        void shouldPersistAndRetrieveEntity() {
            List<NFCDevice> all = repository.findAll();
            assertThat(all)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("id", "version")
                .isEqualTo(testEntity);
        }

        @Test
        void shouldFindByAllFields() {
    
            // Test deviceName field
            List<NFCDevice> byDevicename = repository.findByDevicename(testEntity.getDevicename());
            assertThat(byDevicename)
                .hasSize(1)
                .extracting("deviceName")
                .containsExactly(testEntity.getDevicename());

            // Test deviceSerial field
            List<NFCDevice> byDeviceserial = repository.findByDeviceserial(testEntity.getDeviceserial());
            assertThat(byDeviceserial)
                .hasSize(1)
                .extracting("deviceSerial")
                .containsExactly(testEntity.getDeviceserial());

            // Test location field
            List<NFCDevice> byLocation = repository.findByLocation(testEntity.getLocation());
            assertThat(byLocation)
                .hasSize(1)
                .extracting("location")
                .containsExactly(testEntity.getLocation());

            // Test status field
            List<NFCDevice> byStatus = repository.findByStatus(testEntity.getStatus());
            assertThat(byStatus)
                .hasSize(1)
                .extracting("status")
                .containsExactly(testEntity.getStatus());

            // Test firmwareVersion field
            List<NFCDevice> byFirmwareversion = repository.findByFirmwareversion(testEntity.getFirmwareversion());
            assertThat(byFirmwareversion)
                .hasSize(1)
                .extracting("firmwareVersion")
                .containsExactly(testEntity.getFirmwareversion());

            // Test batteryLevel field
            List<NFCDevice> byBatterylevel = repository.findByBatterylevel(testEntity.getBatterylevel());
            assertThat(byBatterylevel)
                .hasSize(1)
                .extracting("batteryLevel")
                .containsExactly(testEntity.getBatterylevel());

            // Test lastPing field
            List<NFCDevice> byLastping = repository.findByLastping(testEntity.getLastping());
            assertThat(byLastping)
                .hasSize(1)
                .extracting("lastPing")
                .containsExactly(testEntity.getLastping());

            // Test configuration field
            List<NFCDevice> byConfiguration = repository.findByConfiguration(testEntity.getConfiguration());
            assertThat(byConfiguration)
                .hasSize(1)
                .extracting("configuration")
                .containsExactly(testEntity.getConfiguration());

            // Test createdAt field
            List<NFCDevice> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test updatedAt field
            List<NFCDevice> byUpdatedat = repository.findByUpdatedat(testEntity.getUpdatedat());
            assertThat(byUpdatedat)
                .hasSize(1)
                .extracting("updatedAt")
                .containsExactly(testEntity.getUpdatedat());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<NFCDevice> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setDevicename(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setDeviceserial(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setLocation(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setStatus(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setFirmwareversion(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setBatterylevel(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setLastping(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setConfiguration(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            NFCDevice invalidEntity = createTestEntity();
            invalidEntity.setUpdatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<NFCDevice> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_nFCDevices.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        private NFCDevice createTestEntity() {
            NFCDevice entity = new NFCDevice();
            entity.setDevicename(testEntity.getDevicename());
        entity.setDeviceserial(testEntity.getDeviceserial());
        entity.setLocation(testEntity.getLocation());
        entity.setStatus(testEntity.getStatus());
        entity.setFirmwareversion(testEntity.getFirmwareversion());
        entity.setBatterylevel(testEntity.getBatterylevel());
        entity.setLastping(testEntity.getLastping());
        entity.setConfiguration(testEntity.getConfiguration());
        entity.setCreatedat(testEntity.getCreatedat());
        entity.setUpdatedat(testEntity.getUpdatedat());
            return entity;
        }
    }
