package kg.ab.todolist.commons.exceptions;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kg.ab.todolist.commons.enums.ExceptionCode;
import kg.ab.todolist.commons.exceptions.response.ErrorResponse;
import kg.ab.todolist.commons.exceptions.response.ListNullExp;
import kg.ab.todolist.commons.exceptions.response.WrongRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", message = "Successfully handled base exception"),
//            @ApiResponse(code = 404, message = "Resource not found"),
//            @ApiResponse(code = 500, message = "Internal server error")
//    })

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
}
