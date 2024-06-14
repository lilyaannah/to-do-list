package kg.ab.todolist.commons.exceptions;

import kg.ab.todolist.commons.enums.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public  BaseException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}
