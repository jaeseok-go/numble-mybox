package jaeseok.numble.mybox.common.response;

import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class MyBoxResponse {
    private static final String SUCCESS_CODE = ResponseCode.SUCCESS.getCode();
    private Boolean success;
    private String errorCode;
    private Object content;

    public MyBoxResponse(Object content) {
        this.success = true;
        this.errorCode = SUCCESS_CODE;
        this.content = content;
    }

    public MyBoxResponse(MyBoxException exception) {
        String errorCode = exception.getErrorCode();

        this.success = SUCCESS_CODE.equals(errorCode);
        this.errorCode = errorCode;
        this.content = exception.getMessage();
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Object getContent() { return this.content; }
}
