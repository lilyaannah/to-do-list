package kg.ab.todolist.commons.exceptions.response;

import kg.ab.todolist.commons.enums.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private ExceptionCode code;
    private String message;

    public ErrorResponse(ExceptionCode code, String message) {
        this.code = code;
        this.message = message;
    }
}
