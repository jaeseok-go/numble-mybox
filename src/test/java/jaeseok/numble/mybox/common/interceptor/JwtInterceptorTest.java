package jaeseok.numble.mybox.common.interceptor;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("JwtInterceptorTest")
@WebMvcTest(JwtInterceptor.class)
class JwtInterceptorTest {
    @Autowired
    JwtInterceptor jwtInterceptor;

    @MockBean
    JwtHandler jwtHandler;

    @Nested
    @DisplayName("preHandle")
    class PreHandle {
        private MockHttpServletRequest request = new MockHttpServletRequest();
        @Test
        @DisplayName("jwt 유효성 검증")
        void callJwtValidate() {
            // given
            BDDMockito.given(jwtHandler.validate(any(String.class))).willReturn(false);

            request.addHeader("Authorization", "sample.jwt.string");
            request.setRequestURI("/test");
            request.setMethod("GET");

            // when
            Executable authorization = () ->
                    jwtInterceptor.preHandle(request, new MockHttpServletResponse(), new Object());

            // then
            assertThrows(MyBoxException.class, authorization, "Token is expired.");

            // verify
            verify(jwtHandler, times(1)).validate(any(String.class));
        }

        @Test
        @DisplayName("option method request의 경우 validate 체크없이 bypass")
        void optionMethodByPass() throws Exception {
            // given
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.addHeader("Authorization", "sample.jwt.string");
            request.setRequestURI("/test");
            request.setMethod("OPTIONS");

            // when
            Boolean result = jwtInterceptor.preHandle(request, new MockHttpServletResponse(), new Object());

            // then
            assertTrue(result);

            // verify
            verify(jwtHandler, times(0)).validate(any(String.class));
        }
    }

}