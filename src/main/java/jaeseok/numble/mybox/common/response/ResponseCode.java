package jaeseok.numble.mybox.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("00000", "There is no exception."),
    MEMBER_NOT_FOUND("M0001", "Member is not found."),
    INVALID_TOKEN("A0001", "Token is expired."),
    FILE_UPLOAD_FAIL("F0001", "Fail to upload file.");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.message = message;
        this.code = code;
    }
}
