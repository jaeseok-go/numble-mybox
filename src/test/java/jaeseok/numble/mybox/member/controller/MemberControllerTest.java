package jaeseok.numble.mybox.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.member.dto.LoginParam;
import jaeseok.numble.mybox.member.dto.SignUpParam;
import jaeseok.numble.mybox.storage.StorageHandler;
import jaeseok.numble.mybox.util.annotation.IntegrationTest;
import jaeseok.numble.mybox.util.initializer.TestInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class MemberControllerTest {

    @MockBean
    protected StorageHandler storageHandler;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TestInitializer authorizedTestInitializer;

    private String email = "abc@def.ghi";
    private String password = "jklmn!@#$%";

    @Nested
    @DisplayName("회원가입")
    class SignUpTest {

        @Test
        @DisplayName("성공")
        void success() throws Exception {
            // given
            SignUpParam signUpParam = new SignUpParam(email, password);

            // when
            ResultActions resultActions = mockMvc.perform(post("/api/v1/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpParam))
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("success").value(true))
                    .andExpect(jsonPath("errorCode").value(ResponseCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("content.id").isNumber())
                    .andExpect(jsonPath("content.email").value(email));
        }

        @Test
        @DisplayName("중복된 이메일로 회원가입하는 경우 실패")
        void failToDuplicatedEmail() throws Exception {
            // given
            SignUpParam signUpParam = new SignUpParam(email, password);

            mockMvc.perform(post("/api/v1/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpParam))
                    .accept(MediaType.APPLICATION_JSON));

            // when
            ResultActions resultActions = mockMvc.perform(post("/api/v1/member")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpParam))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("success").value(false))
                    .andExpect(jsonPath("errorCode").value(ResponseCode.MEMBER_EXIST.getCode()))
                    .andExpect(jsonPath("content").value(ResponseCode.MEMBER_EXIST.getMessage()));
        }

        @Nested
        @DisplayName("로그인")
        class Login {
            @BeforeEach
            void initMember() throws Exception {
                authorizedTestInitializer.signUp(mockMvc, objectMapper, email, password);
            }

            @Test
            @DisplayName("성공")
            void success() throws Exception {
                // given
                LoginParam loginParam = new LoginParam(email, password);

                // when
                ResultActions resultActions = mockMvc.perform(post("/api/v1/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginParam))
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

                // then
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("success").value(true))
                        .andExpect(jsonPath("errorCode").value(ResponseCode.SUCCESS.getCode()))
                        .andExpect(jsonPath("content.jwt").isString())
                        .andExpect(jsonPath("content.jwt", containsString(".")))
                        .andExpect(jsonPath("content.rootFolderId").isNumber())
                        .andExpect(jsonPath("content.memberInfo.id").isNumber())
                        .andExpect(jsonPath("content.memberInfo.email").value(email))
                        .andExpect(jsonPath("content.memberInfo.usage.b").isNumber())
                        .andExpect(jsonPath("content.memberInfo.usage.kb").isNumber())
                        .andExpect(jsonPath("content.memberInfo.usage.mb").isNumber())
                        .andExpect(jsonPath("content.memberInfo.usage.gb").isNumber());
            }

            @Test
            @DisplayName("패스워드 틀린경우 실패")
            void failToFaultPassword() throws Exception {
                // given
                LoginParam loginParam = new LoginParam(email, "fault_password");

                // when
                ResultActions resultActions = mockMvc.perform(post("/api/v1/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginParam))
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

                // then
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("success").value(false))
                        .andExpect(jsonPath("errorCode").value(ResponseCode.INVALID_PASSWORD.getCode()))
                        .andExpect(jsonPath("content").value(ResponseCode.INVALID_PASSWORD.getMessage()));
            }
        }

        @Nested
        @DisplayName("회원정보 조회")
        class RetrieveMemberInfo {

            private String jwt;

            @BeforeEach
            void initMemberAndJwt() throws Exception {
                jwt = authorizedTestInitializer
                        .memberInitialize(mockMvc, objectMapper, email, password)
                        .getJwt();
            }

            @Test
            @DisplayName("성공")
            void success() throws Exception {
                // given


                // when
                ResultActions resultActions = mockMvc.perform(get("/api/v1/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", jwt)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

                // then
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("success").value(true))
                        .andExpect(jsonPath("errorCode").value(ResponseCode.SUCCESS.getCode()))
                        .andExpect(jsonPath("content.id").isNumber())
                        .andExpect(jsonPath("content.email").value(email))
                        .andExpect(jsonPath("content.usage.b").isNumber())
                        .andExpect(jsonPath("content.usage.kb").isNumber())
                        .andExpect(jsonPath("content.usage.mb").isNumber())
                        .andExpect(jsonPath("content.usage.gb").isNumber());
            }

            @Test
            @DisplayName("헤더에 유효한 토큰이 없는 경우 실패")
            void failToInvalidToken() throws Exception {
                // given


                // when
                ResultActions resultActions = mockMvc.perform(get("/api/v1/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

                // then
                resultActions
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("success").value(false))
                        .andExpect(jsonPath("errorCode").value(ResponseCode.INVALID_TOKEN.getCode()))
                        .andExpect(jsonPath("content").value(ResponseCode.INVALID_TOKEN.getMessage()));
            }
        }
    }
}