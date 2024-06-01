package kg.todolist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = TaskNameIsEmptyException.class)
    public ResponseEntity<String> handleTaskNameIsEmptyException(TaskNameIsEmptyException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = TaskIdNotFoundException.class)
    public ResponseEntity<String> handleTaskIdNotFoundException(TaskIdNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = GetAllTaskListIsNullException.class)
    public ResponseEntity<String> handleGetAllTaskListIsNullException(GetAllTaskListIsNullException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = TaskNameAndStatusNullException.class)
    public ResponseEntity<String> handleTaskNameAndStatusNullException(TaskNameAndStatusNullException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
