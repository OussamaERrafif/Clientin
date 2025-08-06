package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.Training;
    import com.Clientin.Clientin.dto.TrainingDTO;
    import com.Clientin.Clientin.mapper.TrainingMapper;
    import com.Clientin.Clientin.repository.TrainingRepository;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.*;
    import org.mockito.junit.jupiter.MockitoExtension;
    import org.springframework.dao.*;
    import org.springframework.data.domain.*;
    import java.util.*;
    import static org.assertj.core.api.Assertions.*;
    import static org.mockito.ArgumentMatchers.*;
    import static org.mockito.BDDMockito.*;

    @ExtendWith(MockitoExtension.class)
    class TrainingServiceImplTest {

        @Mock
        private TrainingRepository trainingRepository;
        @Mock
        private TrainingMapper trainingMapper;
        @InjectMocks
        private TrainingServiceImpl service;

        private Training entity;
        private TrainingDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<Training> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new Training();
            dto = new TrainingDTO();
            entity.setTitle("Test Title");
        dto.setTitle(entity.getTitle());
        entity.setDescription("Test Description");
        dto.setDescription(entity.getDescription());
        entity.setTrainingType(createTestTrainingType());
        dto.setTrainingType(entity.getTrainingType());
        entity.setDurationHours(42);
        dto.setDurationHours(entity.getDurationHours());
        entity.setContentUrl("Test Contenturl");
        dto.setContentUrl(entity.getContentUrl());
        entity.setPrerequisites("Test Prerequisites");
        dto.setPrerequisites(entity.getPrerequisites());
        entity.setLearningObjectives("Test Learningobjectives");
        dto.setLearningObjectives(entity.getLearningObjectives());
        entity.setStatus(createTestTrainingStatus());
        dto.setStatus(entity.getStatus());
        entity.setMaxParticipants(42);
        dto.setMaxParticipants(entity.getMaxParticipants());
        entity.setPassingScore(42);
        dto.setPassingScore(entity.getPassingScore());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setUpdatedAt(entity.getUpdatedAt());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(trainingMapper.toEntity(any())).willReturn(entity);
            given(trainingRepository.save(any())).willReturn(entity);
            given(trainingMapper.toDTO(any())).willReturn(dto);

            // Act
            TrainingDTO result = service.create(dto);

            // Assert
            assertThat(result.getTitle()).isEqualTo(dto.getTitle());
        assertThat(result.getDescription()).isEqualTo(dto.getDescription());
        assertThat(result.getTrainingType()).isEqualTo(dto.getTrainingType());
        assertThat(result.getDurationHours()).isEqualTo(dto.getDurationHours());
        assertThat(result.getContentUrl()).isEqualTo(dto.getContentUrl());
        assertThat(result.getPrerequisites()).isEqualTo(dto.getPrerequisites());
        assertThat(result.getLearningObjectives()).isEqualTo(dto.getLearningObjectives());
        assertThat(result.getStatus()).isEqualTo(dto.getStatus());
        assertThat(result.getMaxParticipants()).isEqualTo(dto.getMaxParticipants());
        assertThat(result.getPassingScore()).isEqualTo(dto.getPassingScore());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getUpdatedAt()).isEqualTo(dto.getUpdatedAt());
            then(trainingRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(trainingRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(trainingMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<TrainingDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(trainingRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(trainingRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("Training", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(trainingRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(trainingRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(trainingRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<Training> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(trainingRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(trainingRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            TrainingDTO partialDto = new TrainingDTO();
            partialDto.setTitle(dto.getTitle());
            given(trainingRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(trainingMapper).should().partialUpdate(partialDto, entity);
            then(trainingRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(trainingRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(trainingRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }
