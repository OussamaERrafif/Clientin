package com.Clientin.Clientin.repository;

    import com.Clientin.Clientin.entity.PerformanceReview;
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
    class PerformanceReviewRepositoryTest {

        @Autowired
        private PerformanceReviewRepository repository;
        
        @Autowired
        private EntityManager em;

        private PerformanceReview testEntity;

        @BeforeAll
        void setupRelationships() {
            // Pre-create required relationship entities
            User employee = new User();
        em.persist(employee);
        User reviewer = new User();
        em.persist(reviewer);
        }

        @BeforeEach
        void setUp() {
            testEntity = new PerformanceReview();
            testEntity.setEmployeeId("TEST_EMPLOYEEID");
        testEntity.setReviewerId("TEST_REVIEWERID");
        testEntity.setReviewPeriodStart(LocalDate.of(2024, 1, 1));
        testEntity.setReviewPeriodEnd(LocalDate.of(2024, 1, 1));
        testEntity.setOverallScore(new BigDecimal("1234.56"));
        testEntity.setTechnicalSkillsScore(new BigDecimal("1234.56"));
        testEntity.setCommunicationScore(new BigDecimal("1234.56"));
        testEntity.setTeamworkScore(new BigDecimal("1234.56"));
        testEntity.setLeadershipScore(new BigDecimal("1234.56"));
        testEntity.setStrengths("TEST_STRENGTHS");
        testEntity.setAreasForImprovement("TEST_AREASFORIMPROVEMENT");
        testEntity.setGoals("TEST_GOALS");
        testEntity.setReviewerComments("TEST_REVIEWERCOMMENTS");
        testEntity.setEmployeeComments("TEST_EMPLOYEECOMMENTS");
        // TODO: Handle ReviewStatus type for status
        // testEntity.setStatus(/* unknown type */);
        testEntity.setCompletedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        testEntity.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        testEntity.setUpdatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        User employee = UserTestUtils.createTestUser();
        em.persist(employee);
        testEntity.setEmployee(employee);
        User reviewer = UserTestUtils.createTestUser();
        em.persist(reviewer);
        testEntity.setReviewer(reviewer);
            repository.saveAndFlush(testEntity);
            em.clear();
        }

        @AfterEach
        void tearDown() {
            UserTestUtils.cleanupUser(em);
        UserTestUtils.cleanupUser(em);
        }

        @Test
        @Transactional
        void shouldPersistAndRetrieveEntity() {
            List<PerformanceReview> all = repository.findAll();
            assertThat(all)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .ignoringFields("id", "version")
                .isEqualTo(testEntity);
        }

        @Test
        void shouldFindByAllFields() {
    
            // Test employeeId field
            List<PerformanceReview> byEmployeeid = repository.findByEmployeeid(testEntity.getEmployeeid());
            assertThat(byEmployeeid)
                .hasSize(1)
                .extracting("employeeId")
                .containsExactly(testEntity.getEmployeeid());

            // Test reviewerId field
            List<PerformanceReview> byReviewerid = repository.findByReviewerid(testEntity.getReviewerid());
            assertThat(byReviewerid)
                .hasSize(1)
                .extracting("reviewerId")
                .containsExactly(testEntity.getReviewerid());

            // Test reviewPeriodStart field
            List<PerformanceReview> byReviewperiodstart = repository.findByReviewperiodstart(testEntity.getReviewperiodstart());
            assertThat(byReviewperiodstart)
                .hasSize(1)
                .extracting("reviewPeriodStart")
                .containsExactly(testEntity.getReviewperiodstart());

            // Test reviewPeriodEnd field
            List<PerformanceReview> byReviewperiodend = repository.findByReviewperiodend(testEntity.getReviewperiodend());
            assertThat(byReviewperiodend)
                .hasSize(1)
                .extracting("reviewPeriodEnd")
                .containsExactly(testEntity.getReviewperiodend());

            // Test overallScore field
            List<PerformanceReview> byOverallscore = repository.findByOverallscore(testEntity.getOverallscore());
            assertThat(byOverallscore)
                .hasSize(1)
                .extracting("overallScore")
                .containsExactly(testEntity.getOverallscore());

            // Test technicalSkillsScore field
            List<PerformanceReview> byTechnicalskillsscore = repository.findByTechnicalskillsscore(testEntity.getTechnicalskillsscore());
            assertThat(byTechnicalskillsscore)
                .hasSize(1)
                .extracting("technicalSkillsScore")
                .containsExactly(testEntity.getTechnicalskillsscore());

            // Test communicationScore field
            List<PerformanceReview> byCommunicationscore = repository.findByCommunicationscore(testEntity.getCommunicationscore());
            assertThat(byCommunicationscore)
                .hasSize(1)
                .extracting("communicationScore")
                .containsExactly(testEntity.getCommunicationscore());

            // Test teamworkScore field
            List<PerformanceReview> byTeamworkscore = repository.findByTeamworkscore(testEntity.getTeamworkscore());
            assertThat(byTeamworkscore)
                .hasSize(1)
                .extracting("teamworkScore")
                .containsExactly(testEntity.getTeamworkscore());

            // Test leadershipScore field
            List<PerformanceReview> byLeadershipscore = repository.findByLeadershipscore(testEntity.getLeadershipscore());
            assertThat(byLeadershipscore)
                .hasSize(1)
                .extracting("leadershipScore")
                .containsExactly(testEntity.getLeadershipscore());

            // Test strengths field
            List<PerformanceReview> byStrengths = repository.findByStrengths(testEntity.getStrengths());
            assertThat(byStrengths)
                .hasSize(1)
                .extracting("strengths")
                .containsExactly(testEntity.getStrengths());

            // Test areasForImprovement field
            List<PerformanceReview> byAreasforimprovement = repository.findByAreasforimprovement(testEntity.getAreasforimprovement());
            assertThat(byAreasforimprovement)
                .hasSize(1)
                .extracting("areasForImprovement")
                .containsExactly(testEntity.getAreasforimprovement());

            // Test goals field
            List<PerformanceReview> byGoals = repository.findByGoals(testEntity.getGoals());
            assertThat(byGoals)
                .hasSize(1)
                .extracting("goals")
                .containsExactly(testEntity.getGoals());

            // Test reviewerComments field
            List<PerformanceReview> byReviewercomments = repository.findByReviewercomments(testEntity.getReviewercomments());
            assertThat(byReviewercomments)
                .hasSize(1)
                .extracting("reviewerComments")
                .containsExactly(testEntity.getReviewercomments());

            // Test employeeComments field
            List<PerformanceReview> byEmployeecomments = repository.findByEmployeecomments(testEntity.getEmployeecomments());
            assertThat(byEmployeecomments)
                .hasSize(1)
                .extracting("employeeComments")
                .containsExactly(testEntity.getEmployeecomments());

            // Test status field
            List<PerformanceReview> byStatus = repository.findByStatus(testEntity.getStatus());
            assertThat(byStatus)
                .hasSize(1)
                .extracting("status")
                .containsExactly(testEntity.getStatus());

            // Test completedAt field
            List<PerformanceReview> byCompletedat = repository.findByCompletedat(testEntity.getCompletedat());
            assertThat(byCompletedat)
                .hasSize(1)
                .extracting("completedAt")
                .containsExactly(testEntity.getCompletedat());

            // Test createdAt field
            List<PerformanceReview> byCreatedat = repository.findByCreatedat(testEntity.getCreatedat());
            assertThat(byCreatedat)
                .hasSize(1)
                .extracting("createdAt")
                .containsExactly(testEntity.getCreatedat());

            // Test updatedAt field
            List<PerformanceReview> byUpdatedat = repository.findByUpdatedat(testEntity.getUpdatedat());
            assertThat(byUpdatedat)
                .hasSize(1)
                .extracting("updatedAt")
                .containsExactly(testEntity.getUpdatedat());

            // Test employee field
            List<PerformanceReview> byEmployee = repository.findByEmployee(testEntity.getEmployee());
            assertThat(byEmployee)
                .hasSize(1)
                .extracting("employee")
                .containsExactly(testEntity.getEmployee());

            // Test reviewer field
            List<PerformanceReview> byReviewer = repository.findByReviewer(testEntity.getReviewer());
            assertThat(byReviewer)
                .hasSize(1)
                .extracting("reviewer")
                .containsExactly(testEntity.getReviewer());
        }

        @Test
        void shouldReturnEmptyForInvalidId() {
            Optional<PerformanceReview> found = repository.findById("INVALID_ID");
            assertThat(found).isEmpty();
        }

        @Test
        void shouldEnforceBusinessConstraints() {
            // Test null constraints
            
            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setId(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setEmployeeid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setReviewerid(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setReviewperiodstart(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setReviewperiodend(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setOverallscore(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setTechnicalskillsscore(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setCommunicationscore(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setTeamworkscore(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setLeadershipscore(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setStrengths(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setAreasforimprovement(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setGoals(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setReviewercomments(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setEmployeecomments(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setStatus(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setCompletedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setCreatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setUpdatedat(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setEmployee(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            PerformanceReview invalidEntity = createTestEntity();
            invalidEntity.setReviewer(null);
            assertThatThrownBy(() -> repository.save(invalidEntity))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null");

            // Test unique constraints
            
        }

        @Test
        void shouldSupportPaginationAndSorting() {
            repository.save(createTestEntity());
            
            Page<PerformanceReview> page = repository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
            );
            
            assertThat(page.getContent())
                .hasSize(1)
                .extracting("id")
                .containsExactly(repository.count());
        }

        @Test
        @Sql(scripts = "/data/insert_test_performanceReviews.sql")
        void shouldExecuteCustomQueries() {
            // Add custom query tests here
            List<Entity> results = repository.findByCustomCriteria();
            assertThat(results).isNotEmpty();
        }

        @Test
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(performanceReviewService.getEmployee(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/employee", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        @Test
        void getReviewer_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(performanceReviewService.getReviewer(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/reviewer", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private PerformanceReview createTestEntity() {
            PerformanceReview entity = new PerformanceReview();
            entity.setEmployeeid(testEntity.getEmployeeid());
        entity.setReviewerid(testEntity.getReviewerid());
        entity.setReviewperiodstart(testEntity.getReviewperiodstart());
        entity.setReviewperiodend(testEntity.getReviewperiodend());
        entity.setOverallscore(testEntity.getOverallscore());
        entity.setTechnicalskillsscore(testEntity.getTechnicalskillsscore());
        entity.setCommunicationscore(testEntity.getCommunicationscore());
        entity.setTeamworkscore(testEntity.getTeamworkscore());
        entity.setLeadershipscore(testEntity.getLeadershipscore());
        entity.setStrengths(testEntity.getStrengths());
        entity.setAreasforimprovement(testEntity.getAreasforimprovement());
        entity.setGoals(testEntity.getGoals());
        entity.setReviewercomments(testEntity.getReviewercomments());
        entity.setEmployeecomments(testEntity.getEmployeecomments());
        entity.setStatus(testEntity.getStatus());
        entity.setCompletedat(testEntity.getCompletedat());
        entity.setCreatedat(testEntity.getCreatedat());
        entity.setUpdatedat(testEntity.getUpdatedat());
        entity.setEmployee(testEntity.getEmployee());
        entity.setReviewer(testEntity.getReviewer());
            return entity;
        }
    }
