package com.Clientin.Clientin.controller;

    import com.Clientin.Clientin.dto.EmailTemplateDTO;
    import com.Clientin.Clientin.service.EmailTemplateService;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
    import org.springframework.boot.test.mock.mockito.MockBean;
    import org.springframework.data.domain.*;
    import org.springframework.hateoas.*;
    import org.springframework.http.*;
        import org.springframework.test.web.servlet.MockMvc;
    import java.util.*;
    import static org.mockito.BDDMockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @WebMvcTest(EmailTemplateController.class)
    class EmailTemplateControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private EmailTemplateService emailTemplateService;
        @Autowired
        private ObjectMapper objectMapper;

        private final String TEST_ID = "TEST_ID_123";
        private final EmailTemplateDTO validDto = createValidDto();
        private final String BASE_URL = "/api/v1/emailTemplates";

        @Test
        void getById_ShouldReturnResourceWithLinks() throws Exception {
            given(emailTemplateService.findById(TEST_ID)).willReturn(Optional.of(validDto));

            mockMvc.perform(get(BASE_URL + "/{id}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID.toString()))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.collection.href").exists());
        }

        @Test
        void create_ShouldReturnCreatedWithLocation() throws Exception {
            given(emailTemplateService.create(any())).willReturn(validDto);

            mockMvc.perform(post(BASE_URL)
                    
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$._links.self.href").exists());
        }

        @Test
        void create_ShouldRequireAuthentication() throws Exception {
            mockMvc.perform(post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
                .andExpect(status().isUnauthorized());
        }

        @Test
        void create_ShouldValidateInput() throws Exception {
            EmailTemplateDTO invalidDto = new EmailTemplateDTO();

            mockMvc.perform(post(BASE_URL)
                    
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").exists());
        }

        @Test
        void getAll_ShouldReturnPagedResources() throws Exception {
            Page<EmailTemplateDTO> page = new PageImpl<>(List.of(validDto));
            given(emailTemplateService.findAll(any())).willReturn(page);

            mockMvc.perform(get(BASE_URL)
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "id,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.emailTemplateList[0].id").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
        }

        @Test
        void delete_ShouldReturnNoContent() throws Exception {
            given(emailTemplateService.exists(TEST_ID)).willReturn(true);

            mockMvc.perform(delete(BASE_URL + "/{id}", TEST_ID)
                    )
                .andExpect(status().isNoContent());
        }

        @Test
        void partialUpdate_ShouldHandleInvalidData() throws Exception {
            given(emailTemplateService.partialUpdate(eq(TEST_ID), any()))
                .willThrow(new IllegalArgumentException("Invalid data"));

            mockMvc.perform(patch(BASE_URL + "/{id}", TEST_ID)
                    
                    .content("{}"))
                .andExpect(status().isBadRequest());
        }

        private EmailTemplateDTO createValidDto() {
            return EmailTemplateDTO.builder()
                .id(TEST_ID)
                .templateName("Test templateName")
            .subject("Test subject")
            .htmlContent("Test htmlContent")
            .textContent("Test textContent")
            .variables("Test variables")
            .version(42)
            .createdAt("2024-01-01T12:00:00")
            .updatedAt("2024-01-01T12:00:00")
                .build();
        }

        // Error response structure
        static class ErrorResponse {
            public int status;
            public String message;
            public List<ValidationError> errors;
        }

        static class ValidationError {
            public String field;
            public String message;
        }
    }
