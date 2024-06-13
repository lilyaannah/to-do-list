package kg.ab.todolist.commons.exceptions;

import kg.ab.todolist.commons.enums.ExceptionCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Setter
@Getter
public class BaseException extends RuntimeException{
    private ExceptionCode exceptionCode;

    public BaseException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}
