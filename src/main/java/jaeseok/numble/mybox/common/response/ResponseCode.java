package jaeseok.numble.mybox.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("00000000", "There is no exception."),
    MEMBER_NOT_FOUND("MBER0001", "Member is not found."),
    INVALID_TOKEN("AUTH0001", "Token is expired."),
    FILE_UPLOAD_FAIL("FILE0001", "Fail to upload file."),
    FILE_DELETE_FAIL("FILE0002", "Fail to delete file under []."),
    PARENT_NOT_FOUND("FLDR0001", "Parent folder is not found."),
    FOLDER_NOT_FOUND("FLDR0002", "Folder is not found."),
    FOLDER_DELETE_FAIL("FLDR0003", "Fail to delete [] folder.");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.message = message;
        this.code = code;
    }
}
