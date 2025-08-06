package com.Clientin.Clientin.service.impl;

    import com.Clientin.Clientin.entity.EmailTemplate;
    import com.Clientin.Clientin.dto.EmailTemplateDTO;
    import com.Clientin.Clientin.mapper.EmailTemplateMapper;
    import com.Clientin.Clientin.repository.EmailTemplateRepository;
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
    class EmailTemplateServiceImplTest {

        @Mock
        private EmailTemplateRepository emailTemplateRepository;
        @Mock
        private EmailTemplateMapper emailTemplateMapper;
        @InjectMocks
        private EmailTemplateServiceImpl service;

        private EmailTemplate entity;
        private EmailTemplateDTO dto;
        private final String TEST_ID = "TEST_ID_123";
        @Captor
        private ArgumentCaptor<EmailTemplate> entityCaptor;

        @BeforeEach
        void setUp() {
            entity = new EmailTemplate();
            dto = new EmailTemplateDTO();
            entity.setTemplateName("Test Templatename");
        dto.setTemplateName(entity.getTemplateName());
        entity.setTemplateType(createTestTemplateType());
        dto.setTemplateType(entity.getTemplateType());
        entity.setSubject("Test Subject");
        dto.setSubject(entity.getSubject());
        entity.setHtmlContent("Test Htmlcontent");
        dto.setHtmlContent(entity.getHtmlContent());
        entity.setTextContent("Test Textcontent");
        dto.setTextContent(entity.getTextContent());
        entity.setVariables("Test Variables");
        dto.setVariables(entity.getVariables());
        entity.setActive(createTestBoolean());
        dto.setActive(entity.getActive());
        entity.setVersion(42);
        dto.setVersion(entity.getVersion());
        entity.setCreatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setCreatedAt(entity.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.parse("2024-01-01T12:00:00"));
        dto.setUpdatedAt(entity.getUpdatedAt());
        }

        @Test
        void create_ShouldReturnDtoWhenValidInput() {
            // Arrange
            given(emailTemplateMapper.toEntity(any())).willReturn(entity);
            given(emailTemplateRepository.save(any())).willReturn(entity);
            given(emailTemplateMapper.toDTO(any())).willReturn(dto);

            // Act
            EmailTemplateDTO result = service.create(dto);

            // Assert
            assertThat(result.getTemplateName()).isEqualTo(dto.getTemplateName());
        assertThat(result.getTemplateType()).isEqualTo(dto.getTemplateType());
        assertThat(result.getSubject()).isEqualTo(dto.getSubject());
        assertThat(result.getHtmlContent()).isEqualTo(dto.getHtmlContent());
        assertThat(result.getTextContent()).isEqualTo(dto.getTextContent());
        assertThat(result.getVariables()).isEqualTo(dto.getVariables());
        assertThat(result.getActive()).isEqualTo(dto.getActive());
        assertThat(result.getVersion()).isEqualTo(dto.getVersion());
        assertThat(result.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(result.getUpdatedAt()).isEqualTo(dto.getUpdatedAt());
            then(emailTemplateRepository).should().save(entityCaptor.capture());
            assertThat(entityCaptor.getValue()).isEqualTo(entity);
        }

        @Test
        void findById_ShouldReturnDtoWhenEntityExists() {
            // Arrange
            given(emailTemplateRepository.findById(TEST_ID)).willReturn(Optional.of(entity));
            given(emailTemplateMapper.toDTO(entity)).willReturn(dto);

            // Act
            Optional<EmailTemplateDTO> result = service.findById(TEST_ID);

            // Assert
            assertThat(result).containsSame(dto);
            then(emailTemplateRepository).should().findById(TEST_ID);
        }

        @Test
        void update_ShouldThrowWhenEntityNotFound() {
            // Arrange
            given(emailTemplateRepository.existsById(TEST_ID)).willReturn(false);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContainingAll("EmailTemplate", TEST_ID.toString());
        }

        @Test
        void delete_ShouldVerifyExistenceBeforeDeletion() {
            // Arrange
            given(emailTemplateRepository.existsById(TEST_ID)).willReturn(true);

            // Act
            service.delete(TEST_ID);

            // Assert
            then(emailTemplateRepository).should().deleteById(TEST_ID);
        }

        @Test
        void bulkDelete_ShouldValidateCompleteDeletion() {
            // Arrange
            List<String> ids = List.of(TEST_ID, "TEST_ID_123");
            given(emailTemplateRepository.countAllById(ids)).willReturn(1L);

            // Act & Assert
            assertThatThrownBy(() -> service.bulkDelete(ids))
                .isInstanceOf(BulkOperationException.class)
                .hasMessageContaining("1", "2");
        }

        @Test
        void search_ShouldPassSpecificationToRepository() {
            // Arrange
            Specification<EmailTemplate> spec = mock(Specification.class);
            Pageable pageable = PageRequest.of(0, 10);
            given(emailTemplateRepository.findAll(any(), any())).willReturn(Page.empty());

            // Act
            service.search(spec, pageable);

            // Assert
            then(emailTemplateRepository).should().findAll(spec, pageable);
        }

        @Test
        void partialUpdate_ShouldOnlyUpdateChangedFields() {
            // Arrange
            EmailTemplateDTO partialDto = new EmailTemplateDTO();
            partialDto.setTemplatename(dto.getTemplatename());
            given(emailTemplateRepository.findById(TEST_ID)).willReturn(Optional.of(entity));

            // Act
            service.partialUpdate(TEST_ID, partialDto);

            // Assert
            then(emailTemplateMapper).should().partialUpdate(partialDto, entity);
            then(emailTemplateRepository).should().save(entity);
        }

        @Test
        void shouldHandleOptimisticLockingDuringUpdate() {
            // Arrange
            given(emailTemplateRepository.save(any())).willThrow(ObjectOptimisticLockingFailureException.class);
            given(emailTemplateRepository.existsById(TEST_ID)).willReturn(true);

            // Act & Assert
            assertThatThrownBy(() -> service.update(TEST_ID, dto))
                .isInstanceOf(ConcurrentModificationException.class)
                .hasMessageContaining("conflict");
        }

        private String createTestId() {
            return "TEST_ID_123";
        }
    }
