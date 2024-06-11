package kg.ab.todolist.commons.exceptions;

import kg.ab.todolist.commons.enums.ExceptionCode;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
public class BaseException extends RuntimeException{
    private ExceptionCode exceptionCode;

    public BaseException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public HttpStatus getStatus() {
        return exceptionCode.getStatus();
    }
}
