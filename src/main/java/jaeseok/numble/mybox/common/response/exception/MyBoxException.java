package jaeseok.numble.mybox.common.response.exception;

import jaeseok.numble.mybox.common.response.ResponseCode;

public class MyBoxException extends RuntimeException {

    private String errorCode;

    public MyBoxException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.errorCode = responseCode.getCode();
    }

    public MyBoxException(ResponseCode responseCode, String... messageArgs) {
        super(convertMessage(responseCode.getMessage(), messageArgs));
        this.errorCode = responseCode.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }

    private static String convertMessage(String message, String... messageArgs) {
        int time = 0;
        int index = -1;
        while ((index = message.indexOf("[]")) > 0) {
            message = message.substring(0, index)
                    + messageArgs[time++]
                    + message.substring(index+2);
        }
        return message;
    }
}
