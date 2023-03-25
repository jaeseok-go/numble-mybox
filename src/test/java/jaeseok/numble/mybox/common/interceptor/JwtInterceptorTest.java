package jaeseok.numble.mybox.common.interceptor;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtInterceptorTest {
    @Autowired
    HandlerInterceptor jwtInterceptor;
    @Autowired
    JwtHandler jwtHandler;

    @Test
    @DisplayName("Jwt 인증 성공")
    void authSuccess() {
        // given
        String id = "test_id";
        String jwt = jwtHandler.create(id);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", jwt);
        request.setRequestURI("/test");
        request.setMethod("GET");

        // when
        Executable authorization = () ->
                jwtInterceptor.preHandle(request, new MockHttpServletResponse(), new Object());

        // then
        assertDoesNotThrow(authorization);
    }

    @Test
    @DisplayName("Jwt 인증 실패")
    void authFail() {
        // given
        String jwt = "";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", jwt);
        request.setRequestURI("/test");
        request.setMethod("GET");

        // when
        Executable authorization = () ->
                jwtInterceptor.preHandle(request, new MockHttpServletResponse(), new Object());

        // then
        assertThrows(MyBoxException.class, authorization);
    }
}