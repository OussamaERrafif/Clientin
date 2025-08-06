package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.FileUpload;
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
    class FileUploadRepositoryTest {

        @Autowired
        private FileUploadRepository repository;
        
        @Autowired
        private EntityManager em;

        private FileUpload testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            User user = new User();
        em.persist(user);
        }

        @BeforeEach
        void setUp() {
            testEntity = new FileUpload();
            testEntity.setUserId("TEST_USERID");
        testEntity.setOriginalFilename("Sample Originalfilename");
        testEntity.setStoredFilename("Sample Storedfilename");
        testEntity.setFilePath("TEST_FILEPATH");
        testEntity.setContentType("TEST_CONTENTTYPE");
        testEntity.setFileSize(42L);
        // TODO: Handle FileType type for fileType
        // testEntity.setFileType(/* unknown type */);
        testEntity.setEntityType("TEST_ENTITYTYPE");
        testEntity.setEntityId("TEST_ENTITYID");
        testEntity.setFileHash("TEST_FILEHASH");
        testEntity.setIsPublic(true);
        testEntity.setDownloadCount(42L);
        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        testEntity.setExpiresAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        User user = UserTestUtils.createTestUser();
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
            List<FileUpload> all = repository.findAll();
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
            List<FileUpload> byUserid = repository.findByUserid(testEntity.getUserid());
            assertThat(byUserid)
                .hasSize(1)
                .extracting("userId")
                .containsExactly(testEntity.getUserid());

            // Test originalFilename field
            List<FileUpload> byOriginalfilename = repository.findByOriginalfilename(testEntity.getOriginalfilename());
            assertThat(byOriginalfilename)
                .hasSize(1)
                .extracting("originalFilename")
                .containsExactly(testEntity.getOriginalfilename());

            // Test storedFilename field
            List<FileUpload> byStoredfilename = repository.findByStoredfilename(testEntity.getStoredfilename());
            assertThat(byStoredfilename)
                .hasSize(1)
                .extracting("storedFilename")
                .containsExactly(testEntity.getStoredfilename());

            // Test filePath field
            List<FileUpload> byFilepath = repository.findByFilepath(testEntity.getFilepath());
            assertThat(byFilepath)
                .hasSize(1)
                .extracting("filePath")
                .containsExactly(testEntity.getFilepath());

            // Test contentType field
            List<FileUpload> byContenttype = repository.findByContenttype(testEntity.getContenttype());
            assertThat(byContenttype)
                .hasSize(1)
                .extracting("contentType")
                .containsExactly(testEntity.getContenttype());

            // Test fileSize field
            List<FileUpload> byFilesize = repository.findByFilesize(testEntity.getFilesize());
            assertThat(byFilesize)
                .hasSize(1)
                .extracting("fileSize")
                .containsExactly(testEntity.getFilesize());

            // Test fileType field
            List<FileUpload> byFiletype = repository.findByFiletype(testEntity.getFiletype());
            assertThat(byFiletype)
                .hasSize(1)
                .extracting("fileType")
                .containsExactly(testEntity.getFiletype());

            // Test entityType field
            List<FileUpload> byEntitytype = repository.findByEntitytype(testEntity.getEntitytype());
            assertThat(byEntitytype)
                .hasSize(1)
                .extracting("entityType")
                .containsExactly(testEntity.getEntitytype());

            // Test entityId field
            List<FileUpload> byEntityid = repository.findByEntityid(testEntity.getEntityid());
            assertThat(byEntityid)
                .hasSize(1)
                .extracting("entityId")
                .containsExactly(testEntity.getEntityid());

            // Test fileHash field
            List<FileUpload> byFilehash = repository.findByFilehash(testEntity.getFilehash());
            assertThat(byFilehash)
                .hasSize(1)
                .extracting("fileHash")
                .containsExactly(testEntity.getFilehash());

            // Test isPublic field
            List<FileUpload> byIspublic = repository.findByIspublic(testEntity.getIspublic());
            assertThat(byIspublic)
                .hasSize(1)
                .extracting("isPublic")
                .containsExactly(testEntity.getIspublic());

            // Test downloadCount field
            List<FileUpload> byDownloadcount = repository.findByDownloadcount(testEntity.getDownloadcount());
            assertThat(byDownloadcount)
                .hasSize(1)
                .extracting("downloadCount")
                .containsExactly(testEntity.getDownloadcount());

            // Test createdAt field
            List<FileUpload> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test expiresAt field
            List<FileUpload> byExpiresat = repository.findByExpiresat(testEntity.getExpiresat());
            assertThat(byExpiresat)
                .hasSize(1)
                .extracting("expiresAt")
                .containsExactly(testEntity.getExpiresat());

            // Test user field
            List<FileUpload> byUser = repository.findByUser(testEntity.getUser());
            assertThat(byUser)
                .hasSize(1)
                .extracting("user")
                .containsExactly(testEntity.getUser());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<FileUpload> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setUserid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setOriginalfilename(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setStoredfilename(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setFilepath(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setContenttype(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setFilesize(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setFiletype(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setEntitytype(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setEntityid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setFilehash(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setIspublic(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setDownloadcount(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setExpiresat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            FileUpload invalidEntity = createTestEntity();
            invalidEntity.setUser(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<FileUpload> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_fileUploads.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        @Test
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(fileUploadService.getUser(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/user", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private FileUpload createTestEntity() {
            FileUpload entity = new FileUpload();
            entity.setUserid(testEntity.getUserid());
        entity.setOriginalfilename(testEntity.getOriginalfilename());
        entity.setStoredfilename(testEntity.getStoredfilename());
        entity.setFilepath(testEntity.getFilepath());
        entity.setContenttype(testEntity.getContenttype());
        entity.setFilesize(testEntity.getFilesize());
        entity.setFiletype(testEntity.getFiletype());
        entity.setEntitytype(testEntity.getEntitytype());
        entity.setEntityid(testEntity.getEntityid());
        entity.setFilehash(testEntity.getFilehash());
        entity.setIspublic(testEntity.getIspublic());
        entity.setDownloadcount(testEntity.getDownloadcount());
        entity.setCreatedat(testEntity.getCreatedat());
        entity.setExpiresat(testEntity.getExpiresat());
        entity.setUser(testEntity.getUser());
            return entity;
        }
    }
