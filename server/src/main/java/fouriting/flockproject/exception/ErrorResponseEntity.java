package fouriting.flockproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
public class ErrorResponseEntity {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> createResponseEntity(ErrorCode e){
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorResponseEntity(e.getStatus(), e.getErrorCode(), e.getMessage()));
    }
}
