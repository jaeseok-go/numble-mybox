package jaeseok.numble.mybox.member.controller;

import com.google.gson.Gson;
import jaeseok.numble.mybox.common.response.MyBoxResponse;
import jaeseok.numble.mybox.member.dto.LoginDto;
import jaeseok.numble.mybox.member.dto.MemberSignUpDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("회원가입 API 성공")
    void signUpSuccess() throws Exception {
        // given
        MemberSignUpDto signUpInfo = new MemberSignUpDto("test_id2", "password", "test_nickname");
        Gson gson = new Gson();
        String body = gson.toJson(signUpInfo);

        // when
        MockHttpServletResponse response = mockMvc
                .perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        MyBoxResponse myBoxResponse = gson.fromJson(response.getContentAsString(), MyBoxResponse.class);
        Assertions.assertTrue(myBoxResponse.isSuccess());
    }

    @Test
    @DisplayName("회원 로그인 성공")
    void loginSuccess() throws Exception {
        // given
        MemberSignUpDto signUpInfo = new MemberSignUpDto("test_id3", "password", "test_nickname");
        Gson gson = new Gson();
        String signUpBody = gson.toJson(signUpInfo);

        // 회원정보 생성
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpBody))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        LoginDto loginDto = new LoginDto("test_id3", "password");
        String signInBody = gson.toJson(loginDto);

        // when
        MockHttpServletResponse response = mockMvc
                .perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signInBody))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        MyBoxResponse myBoxResponse = gson.fromJson(response.getContentAsString(), MyBoxResponse.class);
        String accessToken = (String) myBoxResponse.getContent();

        // then
        Assertions.assertTrue(myBoxResponse.isSuccess());
        Assertions.assertTrue(accessToken.contains("."));
    }
}