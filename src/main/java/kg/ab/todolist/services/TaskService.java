package kg.ab.todolist.services;

import kg.ab.todolist.commons.enums.ExceptionCode;
import kg.ab.todolist.commons.enums.StatusOfTask;
import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.TaskNameDto;
import kg.ab.todolist.models.Task;
import kg.ab.todolist.models.repositories.TaskRepository;
import kg.ab.todolist.services.validation.TaskValidator;
import kg.ab.todolist.dto.UpdateTaskInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task createNewTask(TaskNameDto taskNameDto) {
        Task task = Task.builder()
                .taskName(taskNameDto.taskName())
                .status(StatusOfTask.NOT_COMPLETED)
                .build();
        TaskValidator.catchTaskNameIsNull().apply(task);
        task = taskRepository.save(task);
        return task;
    }

    public Task getTaskById(Integer id) {
        Optional<Task> optionalTask = Optional.ofNullable(taskRepository.findTaskById(id));
        return optionalTask.orElseThrow(() -> new BaseException(ExceptionCode.TASK_NOT_FOUND));
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        TaskValidator.catchListNullException(taskList).apply(new Task());
        return taskList;
    }

    public Task updateTaskById(UpdateTaskInfoDto updateTaskInfoDto) {
        Task task = Optional.ofNullable(taskRepository.findTaskById(updateTaskInfoDto.id()))
                .orElseThrow(() -> new BaseException(ExceptionCode.TASK_NOT_FOUND));
        TaskValidator.catchTaskNameAndStatusNull(updateTaskInfoDto).apply(new Task());

        Optional.ofNullable(updateTaskInfoDto.newTaskName()).ifPresent(task::setTaskName);
        Optional.ofNullable(updateTaskInfoDto.status()).ifPresent(task::setStatus);
        return taskRepository.save(task);
    }

    public void deleteById(Integer id) {
        Task task = taskRepository.findTaskById(id);
        TaskValidator.catchNullException(ExceptionCode.TASK_NOT_FOUND).apply(task);
        taskRepository.deleteById(task.getId());
    }
}

