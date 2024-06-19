package kg.ab.todolist.api.advice;

import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.response.ErrorResponse;
import kg.ab.todolist.commons.exceptions.ListNullExp;
import kg.ab.todolist.commons.exceptions.WrongRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

import static kg.ab.todolist.commons.enums.ExceptionCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exception) {
        return ResponseEntity
                .status(exception.getExceptionCode().getStatus())
                .body(new ErrorResponse(exception.getExceptionCode(), exception.getMessage()));
    }

    @ExceptionHandler(WrongRequestException.class)
    public ResponseEntity<ErrorResponse> handleIdNotFoundException(BaseException exception) {
        return ResponseEntity
                .status(NOT_FOUND.getStatus())
                .body(new ErrorResponse(exception.getExceptionCode(), exception.getMessage()));
    }

    @ExceptionHandler(ListNullExp.class)
    public ResponseEntity<ErrorResponse> handleTaskNameAndStatusNullException(BaseException exception) {
        return ResponseEntity
                .status(LIST_IS_NULL.getStatus())
                .body(new ErrorResponse(exception.getExceptionCode(), exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse("Not valid input!");
        return ResponseEntity
                .status(NULL.getStatus())
                .body(new ErrorResponse(NULL, message));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new ErrorResponse(NULL, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        String message = "Argument type mismatch: " + exception.getName();
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.builder().code(NULL).message(message).build());
    }

}
