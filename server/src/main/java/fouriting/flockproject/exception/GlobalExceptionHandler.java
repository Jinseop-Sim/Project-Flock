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
    public ResponseEntity<ErrorResponse> handleIdDuplicateException(IdDuplicateException ex){
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(IdNotExistException.class)
    public ResponseEntity<ErrorResponse> handleIdNotExistException(IdNotExistException ex){
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(PasswdNotMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswdNotMatchException(PasswdNotMatchException ex){
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}
