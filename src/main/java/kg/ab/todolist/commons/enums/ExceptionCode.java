package kg.ab.todolist.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    LIST_IS_NULL(1, "Пустой список", HttpStatus.NO_CONTENT),
    NOT_FOUND(2, "Не найдено", HttpStatus.NOT_FOUND),
    NULL(3, "Не может быть null", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus status;
}
