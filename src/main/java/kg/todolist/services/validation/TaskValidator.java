package kg.todolist.services.validation;

import kg.todolist.commons.enums.ExceptionCode;
import kg.todolist.commons.exceptions.BaseException;
import kg.todolist.dto.UpdateTaskInfoDto;
import kg.todolist.models.Task;

import java.util.List;
import java.util.function.Function;

import static kg.todolist.commons.enums.ExceptionCode.*;

public interface TaskValidator extends Function<Task, Boolean> {
    default TaskValidator and(TaskValidator other) {
        return num -> {
            boolean result = this.apply(num);
            return result &&
                    other.apply(num);
        };
    }

    static TaskValidator catchNullException(ExceptionCode exceptionCode) {
        return task -> {
            if (task == null) {
                throw new BaseException(exceptionCode);
            }
            return true;
        };
    }


    static TaskValidator catchListNullException(List taskList) {
        return task -> {
            if (taskList.isEmpty()) {
                throw new BaseException(LIST_IS_NULL);
            }
            return true;
        };
    }

    static TaskValidator catchTaskNameIsNull() {
        return task -> {
            if (task.getTaskName() == null || task.getTaskName().isEmpty()) {
                throw new BaseException(TASK_NAME_IS_NULL);
            }
            return true;
        };
    }

    static TaskValidator catchTaskNameAndStatusNull(UpdateTaskInfoDto updateTaskInfoDto) {
        return task -> {
            if (updateTaskInfoDto.newTaskName() == null && updateTaskInfoDto.status() == null) {
                throw new BaseException(TASK_NAME_AND_STATUS_NULL);
            }
            return true;
        };
    }
}
