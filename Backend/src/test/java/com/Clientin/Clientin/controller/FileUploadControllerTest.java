package com.Clientin.Clientin.controller;

    import com.Clientin.Clientin.dto.FileUploadDTO;
    import com.Clientin.Clientin.service.FileUploadService;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
    import org.springframework.boot.test.mock.mockito.MockBean;
    import org.springframework.data.domain.*;
    import org.springframework.hateoas.*;
    import org.springframework.http.*;
    import org.springframework.security.test.context.support.WithMockUser;
    import org.springframework.test.web.servlet.MockMvc;
    import java.util.*;
    import static org.mockito.BDDMockito.*;
    import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @WebMvcTest(FileUploadController.class)
    class FileUploadControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private FileUploadService fileUploadService;
        @Autowired
        private ObjectMapper objectMapper;

        private final String TEST_ID = "TEST_ID_123";
        private final FileUploadDTO validDto = createValidDto();
        private final String BASE_URL = "/api/v1/fileUploads";

        @Test
        @WithMockUser(authorities = "SCOPE_FILEUPLOAD_READ")
        void getById_ShouldReturnResourceWithLinks() throws Exception {
            given(fileUploadService.findById(TEST_ID)).willReturn(Optional.of(validDto));

            mockMvc.perform(get(BASE_URL + "/{id}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID.toString()))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.collection.href").exists());
        }

        @Test
        @WithMockUser(authorities = "SCOPE_FILEUPLOAD_WRITE")
        void create_ShouldReturnCreatedWithLocation() throws Exception {
            given(fileUploadService.create(any())).willReturn(validDto);

            mockMvc.perform(post(BASE_URL)
                    .with(csrf())
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
        @WithMockUser(authorities = "SCOPE_FILEUPLOAD_WRITE")
        void create_ShouldValidateInput() throws Exception {
            FileUploadDTO invalidDto = new FileUploadDTO();

            mockMvc.perform(post(BASE_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].field").exists());
        }

        @Test
        @WithMockUser(authorities = "SCOPE_FILEUPLOAD_READ")
        void getAll_ShouldReturnPagedResources() throws Exception {
            Page<FileUploadDTO> page = new PageImpl<>(List.of(validDto));
            given(fileUploadService.findAll(any())).willReturn(page);

            mockMvc.perform(get(BASE_URL)
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "id,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.fileUploadList[0].id").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
        }

        @Test
        @WithMockUser(authorities = "SCOPE_FILEUPLOAD_DELETE")
        void delete_ShouldReturnNoContent() throws Exception {
            given(fileUploadService.exists(TEST_ID)).willReturn(true);

            mockMvc.perform(delete(BASE_URL + "/{id}", TEST_ID)
                    .with(csrf()))
                .andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser(authorities = "SCOPE_FILEUPLOAD_UPDATE")
        void partialUpdate_ShouldHandleInvalidData() throws Exception {
            given(fileUploadService.partialUpdate(eq(TEST_ID), any()))
                .willThrow(new IllegalArgumentException("Invalid data"));

            mockMvc.perform(patch(BASE_URL + "/{id}", TEST_ID)
                    .with(csrf())
                    .content("{}"))
                .andExpect(status().isBadRequest());
        }

        
        @Test
        @WithMockUser(authorities = "SCOPE_FILEUPLOAD_READ")
        void getUser_ShouldReturnRelatedResources() throws Exception {
            List<UserDTO> items = List.of(new UserDTO());
            given(fileUploadService.getUser(TEST_ID))
                .willReturn(items);

            mockMvc.perform(get(BASE_URL + "/{id}/user", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.UserList").exists());
        }

        private FileUploadDTO createValidDto() {
            return FileUploadDTO.builder()
                .id(TEST_ID)
                .userId("Test userId")
            .originalFilename("Test originalFilename")
            .storedFilename("Test storedFilename")
            .filePath("Test filePath")
            .contentType("Test contentType")
            .entityType("Test entityType")
            .entityId("Test entityId")
            .fileHash("Test fileHash")
            .createdAt("2024-01-01T12:00:00")
            .expiresAt("2024-01-01T12:00:00")
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