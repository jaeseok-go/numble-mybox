package jaeseok.numble.mybox.util.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jaeseok.numble.mybox.member.dto.LoginResponse;
import org.springframework.test.web.servlet.MockMvc;

public interface TestInitializer {
    public void signUp(MockMvc mockMvc, ObjectMapper objectMapper, String email, String password) throws Exception;

    public LoginResponse login(MockMvc mockMvc, ObjectMapper objectMapper, String email, String password) throws Exception;

    public LoginResponse memberInitialize(MockMvc mockMvc, ObjectMapper objectMapper, String email, String password) throws Exception;
}
