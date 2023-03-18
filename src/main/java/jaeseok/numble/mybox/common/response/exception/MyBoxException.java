package jaeseok.numble.mybox.common.response.exception;

import jaeseok.numble.mybox.common.response.ResponseCode;

public class MyBoxException extends RuntimeException {

    private String errorCode;

    public MyBoxException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.errorCode = responseCode.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
