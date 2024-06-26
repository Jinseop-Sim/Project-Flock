package fouriting.flockproject.exception.custom;

import fouriting.flockproject.exception.ErrorCode;
import lombok.Getter;

@Getter
public class IdDuplicateException extends RuntimeException{
    private ErrorCode errorCode;

    public IdDuplicateException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
