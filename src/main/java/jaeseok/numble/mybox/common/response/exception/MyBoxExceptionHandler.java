package jaeseok.numble.mybox.common.response.exception;

import jaeseok.numble.mybox.common.response.MyBoxResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class MyBoxExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MyBoxException.class)
    public final ResponseEntity<MyBoxResponse> handleMyBoxException(MyBoxException e) {
        return ResponseEntity.ok(new MyBoxResponse(e));
    }
}
