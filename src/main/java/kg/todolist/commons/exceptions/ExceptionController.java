package kg.todolist.commons.exceptions;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handleBaseException(BaseException exception) {
        return ResponseEntity
                .status(exception.getStatus()).body(exception.getMessage());
    }
}
