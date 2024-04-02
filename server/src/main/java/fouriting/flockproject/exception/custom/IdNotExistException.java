package fouriting.flockproject.exception.custom;

import fouriting.flockproject.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdNotExistException extends RuntimeException{
    private ErrorCode errorCode;
}
