package jaeseok.numble.mybox.file.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jaeseok.numble.mybox.common.response.MyBoxResponse;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.member.dto.LoginParam;
import jaeseok.numble.mybox.member.dto.LoginResponse;
import jaeseok.numble.mybox.member.dto.SignUpParam;
import jaeseok.numble.mybox.util.annotation.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Nested
    @DisplayName("파일 업로드")
    class Upload {

        private String email = "test@test.test";
        private String password = "test_password";
        private String jwt;

        private Long rootFolderId;

        @BeforeEach
        void initMemberAndJwt() throws Exception {
            // 회원가입
            SignUpParam signUpParam = new SignUpParam(email, password);
            mockMvc.perform(post("/api/v1/member")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpParam))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // 로그인
            LoginParam loginParam = new LoginParam(email, password);
            ResultActions resultActions = mockMvc.perform(post("/api/v1/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginParam))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            String content = resultActions.andReturn().getResponse().getContentAsString();
            MyBoxResponse myBoxResponse = objectMapper.readValue(content, MyBoxResponse.class);

            String json = objectMapper.writeValueAsString(myBoxResponse.getContent());
            LoginResponse loginResponse = objectMapper.readValue(json, LoginResponse.class);

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
    }
}