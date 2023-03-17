package jaeseok.numble.mybox.common.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS("00000", "there is no exception");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.message = message;
        this.code = code;
    }
}
