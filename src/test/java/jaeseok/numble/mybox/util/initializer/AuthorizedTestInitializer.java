package jaeseok.numble.mybox.util.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jaeseok.numble.mybox.common.response.MyBoxResponse;
import jaeseok.numble.mybox.member.dto.LoginParam;
import jaeseok.numble.mybox.member.dto.LoginResponse;
import jaeseok.numble.mybox.member.dto.SignUpParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
public class AuthorizedTestInitializer implements TestInitializer{
    @Override
    public void signUp(MockMvc mockMvc, ObjectMapper objectMapper, String email, String password) throws Exception {
        SignUpParam signUpParam = new SignUpParam(email, password);
        mockMvc.perform(post("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpParam))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Override
    public LoginResponse login(MockMvc mockMvc, ObjectMapper objectMapper, String email, String password) throws Exception {
        LoginParam loginParam = new LoginParam(email, password);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginParam))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        String content = resultActions.andReturn().getResponse().getContentAsString();
        MyBoxResponse myBoxResponse = objectMapper.readValue(content, MyBoxResponse.class);

        String json = objectMapper.writeValueAsString(myBoxResponse.getContent());
        return objectMapper.readValue(json, LoginResponse.class);
    }

    @Override
    public LoginResponse memberInitialize(MockMvc mockMvc, ObjectMapper objectMapper, String email, String password) throws Exception {
        signUp(mockMvc, objectMapper, email, password);
        return login(mockMvc, objectMapper, email, password);
    }
}
