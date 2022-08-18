package fouriting.flockproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404, "COMMON_ERR_404", "PAGE_NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "COMMON_ERR", "INTER SERVER ERROR!"),
    ID_DUPLICATION(400, "MEMBER_ERR", "ID DUPLICATED!"),
    ID_NOT_EXIST(400, "NO_ID_ERR", "ID NOT EXIST!"),
    PW_NOT_MATCH(400, "PW_NOT_MATCH", "PW NOT MATCH!"),
    EXPIRED_JWT(403, "EXPIRED_JWT", "EXPIRED ACCESS TOKEN!"),
    ACCESS_DENIED(403, "ACCESS_DENIED", "ACCESS DENIED!"),
    UNKNOWN_ERROR(400, "UNKNOWN_ERROR", "UNKNOWN ERROR!"),
    WRONG_TYPE_TOKEN(400, "WRONG_TYPE_TOKEN", "WRONG TYPE TOKEN!");

    private int status;
    private String errorCode;
    private String message;
}
