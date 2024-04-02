package fouriting.flockproject.exception;

import fouriting.flockproject.exception.custom.IdDuplicateException;
import fouriting.flockproject.exception.custom.IdNotExistException;
import fouriting.flockproject.exception.custom.PasswdNotMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IdDuplicateException.class)
    public ResponseEntity<ErrorResponseEntity> handleIdDuplicateException(IdDuplicateException ex){
        return ErrorResponseEntity.createResponseEntity(ex.getErrorCode());
    }

    @ExceptionHandler(IdNotExistException.class)
    public ResponseEntity<ErrorResponseEntity> handleIdNotExistException(IdNotExistException ex){
        return ErrorResponseEntity.createResponseEntity(ex.getErrorCode());
    }

    @ExceptionHandler(PasswdNotMatchException.class)
    public ResponseEntity<ErrorResponseEntity> handlePasswdNotMatchException(PasswdNotMatchException ex){
        return ErrorResponseEntity.createResponseEntity(ex.getErrorCode());
    }
}
