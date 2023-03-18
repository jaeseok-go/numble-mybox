package jaeseok.numble.mybox.common.response;

import jaeseok.numble.mybox.common.response.exception.MyBoxException;

public class MyBoxResponse {
    private static final String SUCCESS_CODE = ResponseCode.SUCCESS.getCode();
    private Boolean success;
    private String errorCode;
    private Object data;

    public MyBoxResponse(Object data) {
        this.success = true;
        this.errorCode = SUCCESS_CODE;
        this.data = data;
    }

    public MyBoxResponse(MyBoxException exception) {
        String errorCode = exception.getErrorCode();

        this.success = SUCCESS_CODE.equals(errorCode);
        this.errorCode = errorCode;
        this.data = exception.getMessage();
    }
}
