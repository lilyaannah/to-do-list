package kg.ab.todolist.api.advice;

import ch.qos.logback.core.spi.ErrorCodes;
import kg.ab.todolist.commons.enums.ExceptionCode;
import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.commons.exceptions.ErrorResponse;
import kg.ab.todolist.commons.exceptions.ListNullExp;
import kg.ab.todolist.commons.exceptions.WrongRequestException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exception) {
        return ResponseEntity
                .status(ExceptionCode.SUCCESS.getStatus())
                .body(new ErrorResponse(exception.getExceptionCode(), exception.getMessage()));
    }

    @ExceptionHandler(WrongRequestException.class)
    public ResponseEntity<ErrorResponse> handleIdNotFoundException(BaseException exception) {
        return ResponseEntity
                .status(ExceptionCode.TASK_NOT_FOUND.getStatus())
                .body(new ErrorResponse(exception.getExceptionCode(), exception.getMessage()));
    }

    @ExceptionHandler(ListNullExp.class)
    public ResponseEntity<ErrorResponse> handleTaskNameAndStatusNullException(BaseException exception) {
        return ResponseEntity
                .status(ExceptionCode.LIST_IS_NULL.getStatus())
                .body(new ErrorResponse(exception.getExceptionCode(), exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleMethodNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("Not valid input!");
        return ResponseEntity.badRequest().body(message);
    }

}
