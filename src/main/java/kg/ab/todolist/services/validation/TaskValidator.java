package kg.ab.todolist.services.validation;

import kg.ab.todolist.commons.enums.ExceptionCode;
import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.UpdateTaskInfoDto;
import kg.ab.todolist.models.Task;
import java.util.function.Function;

public interface TaskValidator extends Function<Task, Boolean> {
    static TaskValidator catchTaskNameAndStatusNull(UpdateTaskInfoDto updateTaskInfoDto) {
        return task -> {
            if (updateTaskInfoDto.newTaskName() == null && updateTaskInfoDto.status() == null) {
                throw new BaseException(ExceptionCode.NOT_FOUND);
            }
            return true;
        };
    }
}
