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
        task = taskRepository.save(task);
        return task;
    }

    public Task getTaskById(Integer id) {
        Optional<Task> optionalTask = Optional.ofNullable(taskRepository.findTaskById(id));
        return optionalTask.orElseThrow(() -> new BaseException(ExceptionCode.NOT_FOUND));
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        if (taskList.isEmpty()) {
            throw new BaseException(ExceptionCode.LIST_IS_NULL);
        }
        return taskList;
    }

    public Task updateTaskById(UpdateTaskInfoDto updateTaskInfoDto) {
        Task task = getTaskById(updateTaskInfoDto.id());
        TaskValidator.catchTaskNameAndStatusNull(updateTaskInfoDto).apply(task);

        task.setTaskName(updateTaskInfoDto.newTaskName() != null ?
                updateTaskInfoDto.newTaskName() : task.getTaskName());

        task.setStatus(updateTaskInfoDto.status() != null ?
                updateTaskInfoDto.status() : task.getStatus());

        return taskRepository.save(task);
    }

    public void deleteById(Integer id) {
        Task task = getTaskById(id);
        taskRepository.deleteById(task.getId());
    }
}

