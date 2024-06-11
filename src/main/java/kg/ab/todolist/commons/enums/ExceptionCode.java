package kg.ab.todolist.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    SUCCESS(1, "Успешно", HttpStatus.OK),
    LIST_IS_NULL(2, "Пустой список", HttpStatus.NOT_FOUND),
    TASK_ID_NOT_FOUND(3, "ID задачи не найден", HttpStatus.NOT_FOUND),
    TASK_NAME_AND_STATUS_NULL(4, "Название задачи и статус не найден", HttpStatus.NOT_FOUND),
    TASK_NAME_IS_NULL(5, "Не введено название задачи", HttpStatus.NOT_FOUND),
    TASK_NOT_FOUND(6, "Задача не найдена", HttpStatus.NOT_FOUND);

    private int code;
    private String message;
    private HttpStatus status;
}
