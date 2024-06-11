package kg.todolist.services;

import kg.todolist.commons.exceptions.BaseException;
import kg.todolist.dto.TaskNameDto;
import kg.todolist.commons.enums.StatusOfTask;
import kg.todolist.dto.UpdateTaskInfoDto;
import kg.todolist.models.Task;
import kg.todolist.models.repositories.TaskRepository;
import kg.todolist.services.validation.TaskValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static kg.todolist.commons.enums.ExceptionCode.TASK_ID_NOT_FOUND;

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
        return optionalTask.orElseThrow(() -> new BaseException(TASK_ID_NOT_FOUND));
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        TaskValidator.catchListNullException(taskList).apply(new Task());
        return taskList;
    }

    public void updateTaskById(UpdateTaskInfoDto updateTaskInfoDto) {
        Optional<Task> optionalTask = Optional.ofNullable(taskRepository.findTaskById(updateTaskInfoDto.id()));
        Task task = optionalTask.orElseThrow(() -> new BaseException(TASK_ID_NOT_FOUND));

        TaskValidator.catchTaskNameAndStatusNull(updateTaskInfoDto).apply(new Task());

        Optional.ofNullable(updateTaskInfoDto.newTaskName()).ifPresent(task::setTaskName);
        Optional.ofNullable(updateTaskInfoDto.status()).ifPresent(task::setStatus);
        taskRepository.save(task);
    }

    public void deleteById(Integer id) {
        Task task = taskRepository.findTaskById(id);
        TaskValidator.catchNullException(TASK_ID_NOT_FOUND).apply(task);
        taskRepository.deleteById(task.getId());
    }
}

