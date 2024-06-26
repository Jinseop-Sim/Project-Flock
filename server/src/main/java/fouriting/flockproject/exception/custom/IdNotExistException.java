package fouriting.flockproject.exception.custom;

import fouriting.flockproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class IdNotExistException extends RuntimeException{
    private ErrorCode errorCode;

    public IdNotExistException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
