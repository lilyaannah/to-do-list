package kg.ab.todolist.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    SUCCESS(1, "Успешно", HttpStatus.OK),
    LIST_IS_NULL(2, "Пустой список", HttpStatus.NOT_FOUND),
    TASK_NOT_FOUND(3, "Не верное заполнение запроса", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus status;
}
