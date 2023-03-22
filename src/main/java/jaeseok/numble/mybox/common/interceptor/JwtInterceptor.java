package jaeseok.numble.mybox.common.interceptor;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtHandler jwtHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        validate(request);
        return true;
    }

    private void validate(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        // options request -> bypass
        if (!isOptions(request) && !jwtHandler.validate(token)) {
            throw new MyBoxException(ResponseCode.INVALID_TOKEN);
        }
    }

    private boolean isOptions(HttpServletRequest request) {
        return "OPTIONS".equals(request.getMethod());
    }
}
