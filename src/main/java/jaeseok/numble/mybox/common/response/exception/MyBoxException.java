package jaeseok.numble.mybox.common.response.exception;

import jaeseok.numble.mybox.common.response.ErrorCode;

public class MyBoxException extends RuntimeException {

    private String errorCode;

    public MyBoxException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
