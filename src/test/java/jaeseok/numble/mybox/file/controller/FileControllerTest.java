package jaeseok.numble.mybox.file.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.member.dto.LoginResponse;
import jaeseok.numble.mybox.util.annotation.IntegrationTest;
import jaeseok.numble.mybox.util.initializer.TestInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class FileControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ResourceLoader resourceLoader;

    @Autowired
    TestInitializer authorizedTestInitializer;

    @Nested
    @DisplayName("파일 업로드")
    class Upload {

        private String email = "test@test.test";
        private String password = "test_password";
        private String jwt;
        private Long rootFolderId;

        @BeforeEach
        void initMemberAndJwt() throws Exception {
            LoginResponse loginResponse = authorizedTestInitializer
                    .memberInitialize(mockMvc, objectMapper, email, password);

            jwt = loginResponse.getJwt();
            rootFolderId = loginResponse.getRootFolderId();
        }

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            // given
            String fileName = "test_image";
            String originalFileName = fileName + ".png";
            Resource resource = resourceLoader.getResource("classpath:/img/"+originalFileName);

            MockMultipartFile multipartFile =
                    new MockMultipartFile(
                            "file",
                            originalFileName,
                            MediaType.MULTIPART_FORM_DATA_VALUE,
                            resource.getInputStream());

            // when
            ResultActions resultActions = mockMvc.perform(multipart("/api/v1/" + rootFolderId + "/file")
                            .file(multipartFile)
                            .header("Authorization", jwt))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("success").value(true))
                    .andExpect(jsonPath("errorCode").value(ResponseCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("content.id").isNumber())
                    .andExpect(jsonPath("content.name").value(originalFileName))
                    .andExpect(jsonPath("content.createdAt").exists())
                    .andExpect(jsonPath("content.modifiedAt").exists())
                    .andExpect(jsonPath("content.size.b").isNumber())
                    .andExpect(jsonPath("content.size.kb").isNumber())
                    .andExpect(jsonPath("content.size.mb").isNumber())
                    .andExpect(jsonPath("content.size.gb").isNumber());
        }

        @Test
        @DisplayName("폴더의 정보가 유효하지 않는 경우 실패")
        void failToFaultFolder() throws Exception {
            // given
            String fileName = "test_image";
            String originalFileName = fileName + ".png";
            Resource resource = resourceLoader.getResource("classpath:/img/"+originalFileName);

            MockMultipartFile multipartFile =
                    new MockMultipartFile(
                            "file",
                            originalFileName,
                            MediaType.MULTIPART_FORM_DATA_VALUE,
                            resource.getInputStream());

            Long faultFolderId = Long.MAX_VALUE;

            // when
            ResultActions resultActions = mockMvc.perform(multipart("/api/v1/" + faultFolderId + "/file")
                            .file(multipartFile)
                            .header("Authorization", jwt))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("success").value(false))
                    .andExpect(jsonPath("errorCode").value(ResponseCode.PARENT_NOT_FOUND.getCode()))
                    .andExpect(jsonPath("content").value(ResponseCode.PARENT_NOT_FOUND.getMessage()));
        }

        @Test
        @DisplayName("업로드 파일과 동일한 이름이 이미 폴더에 존재하는 경우 실패")
        void failToOverlappedFileName() throws Exception {
            // given
            String fileName = "test_image";
            String originalFileName = fileName + ".png";
            Resource resource = resourceLoader.getResource("classpath:/img/"+originalFileName);

            MockMultipartFile multipartFile =
                    new MockMultipartFile(
                            "file",
                            originalFileName,
                            MediaType.MULTIPART_FORM_DATA_VALUE,
                            resource.getInputStream());

            mockMvc.perform(multipart("/api/v1/" + rootFolderId + "/file")
                            .file(multipartFile)
                            .header("Authorization", jwt))
                    .andDo(print());


            // when
            ResultActions resultActions = mockMvc.perform(multipart("/api/v1/" + rootFolderId + "/file")
                            .file(multipartFile)
                            .header("Authorization", jwt))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("success").value(false))
                    .andExpect(jsonPath("errorCode").value(ResponseCode.FILE_NAME_EXIST.getCode()))
                    .andExpect(jsonPath("content").value(ResponseCode.FILE_NAME_EXIST.getMessage()));
        }
    }
}