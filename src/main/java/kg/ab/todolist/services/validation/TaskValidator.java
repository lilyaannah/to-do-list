package kg.ab.todolist.services.validation;

import kg.ab.todolist.commons.enums.ExceptionCode;
import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.request.UpdateTaskInfoDto;
import kg.ab.todolist.models.Task;
import java.util.function.Function;

public interface TaskValidator extends Function<Task, Boolean> {
    static TaskValidator catchTaskNameAndStatusNull(UpdateTaskInfoDto updateTaskInfoDto) {
        return task -> {
            if (updateTaskInfoDto.getNewTaskName() == null && updateTaskInfoDto.getStatus() == null) {
                throw new BaseException(ExceptionCode.NOT_FOUND);
            }
            return true;
        };
    }
}
