package fouriting.flockproject.exception.custom;

import fouriting.flockproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PasswdNotMatchException extends RuntimeException{
    private ErrorCode errorCode;

    public PasswdNotMatchException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
