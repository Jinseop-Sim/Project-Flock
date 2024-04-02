package fouriting.flockproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND(404, "COMMON_ERR_404", "PAGE_NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "COMMON_ERR", "INTER SERVER ERROR!"),
    ID_DUPLICATION(400, "MEMBER_ERR", "이미 존재하는 아이디입니다."),
    ID_NOT_EXIST(400, "NO_ID_ERR", "존재하지 않는 사용자입니다."),
    PW_NOT_MATCH(400, "PW_NOT_MATCH", "비밀번호를 잘못 입력하였습니다."),
    ACCESS_DENIED(403, "ACCESS_DENIED", "로그인 해주시기 바랍니다."),
    UNKNOWN_ERROR(400, "UNKNOWN_ERROR", "UNKNOWN ERROR!");

    private int status;
    private String errorCode;
    private String message;
}
