package kg.ab.todolist.commons.exceptions;

import kg.ab.todolist.commons.enums.ExceptionCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private ExceptionCode code;
    private String message;

    public ErrorResponse(ExceptionCode code, String message) {
        this.code = code;
        this.message = message;
    }
}
