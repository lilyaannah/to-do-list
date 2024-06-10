package kg.todolist.services;

import kg.todolist.dto.TaskNameDto;
import kg.todolist.commons.enums.StatusOfTask;
import kg.todolist.dto.UpdateTaskInfoDto;
import kg.todolist.models.Task;
import kg.todolist.models.repositories.TaskRepository;
import kg.todolist.services.validation.TaskValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        task=taskRepository.save(task);
        return task;
    }

    public Task getTaskById(Integer id) {
        Task task = taskRepository.findTaskById(id);
        TaskValidator.catchNullException(TASK_ID_NOT_FOUND).apply(task);
        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        TaskValidator.catchListNullException(taskList).apply(new Task());
        return taskList;
    }

    public void updateTaskById(UpdateTaskInfoDto updateTaskInfoDto) {
        Task task = taskRepository.findTaskById(updateTaskInfoDto.id());
        TaskValidator.catchNullException(TASK_ID_NOT_FOUND).apply(task);

        TaskValidator.catchTaskNameAndStatusNull(updateTaskInfoDto).apply(new Task());
        if (updateTaskInfoDto.newTaskName() != null) {
            task.setTaskName(updateTaskInfoDto.newTaskName());
        }
        if (updateTaskInfoDto.status() != null) {
            task.setStatus( updateTaskInfoDto.status());
        }
        taskRepository.save(task);
    }

    public void deleteById(Integer id) {
        Task task = taskRepository.findTaskById(id);
        TaskValidator.catchNullException(TASK_ID_NOT_FOUND).apply(task);
        taskRepository.deleteById(task.getId());
    }
}

