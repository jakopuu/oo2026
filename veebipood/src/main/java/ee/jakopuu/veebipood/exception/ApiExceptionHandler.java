package ee.jakopuu.veebipood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessege> handleException(RuntimeException ex){
        ErrorMessege errorMessege = new ErrorMessege();
        errorMessege.setMessega(ex.getMessage());
        errorMessege.setStatus(HttpStatus.BAD_REQUEST.value());
        errorMessege.setTimestamp(new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessege);

    }
}
