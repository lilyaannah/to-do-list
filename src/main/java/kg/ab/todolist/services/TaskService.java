package kg.ab.todolist.services;

import kg.ab.todolist.commons.enums.ExceptionCode;
import kg.ab.todolist.commons.enums.Status;
import kg.ab.todolist.commons.enums.StatusOfTask;
import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.request.TaskNameDto;
import kg.ab.todolist.dto.TaskResponse;
import kg.ab.todolist.models.Task;
import kg.ab.todolist.models.repositories.TaskRepository;
import kg.ab.todolist.services.validation.TaskValidator;
import kg.ab.todolist.dto.request.UpdateTaskInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    /**
     * Добавление новой задачи
     *
     * @RequestBody TaskNameDto - taskName - описание задачи
     * автоматически статус задачи NOT_COMPLETED
     * статус действия CREATED
     * return TaskResponse
     */
    public TaskResponse createNewTask(TaskNameDto taskNameDto) {
        Task task = taskRepository.save(Task.builder()
                .taskName(taskNameDto.taskName())
                .taskStatus(StatusOfTask.NOT_COMPLETED)
                .status(Status.CREATED)
                .build());

        return TaskResponse.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .taskStatus(StatusOfTask.NOT_COMPLETED)
                .build();
    }

    /**
     * Получение задачи по id
     *
     * @RequestParam - id - идентификатор задачи
     * если статус состояния задачи DELETED выводится не найдено
     * return TaskResponse
     */
    public TaskResponse getTaskById(Integer id) {
        Task task = taskRepository.findTaskById(id)
                .filter(sm -> sm.getStatus() != Status.DELETED)
                .orElseThrow(() -> new BaseException(ExceptionCode.NOT_FOUND));
        return TaskResponse.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .taskStatus(task.getTaskStatus())
                .build();
    }

    /**
     * Получение списка всех задач
     * кроме тех задач у которых статус состояния DELETED
     * return TaskResponse
     */
    public List<TaskResponse> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        if (taskList.isEmpty()) {
            throw new BaseException(ExceptionCode.LIST_IS_NULL);
        }
        return taskList.stream()
                .filter(task -> task.getStatus() != Status.DELETED)
                .map(task -> new TaskResponse(task.getId(), task.getTaskName(), task.getTaskStatus())).toList();
    }

    /**
     * @RequestBody UpdateTaskInfoDto - taskStatus - статус задачи (COMPLETED-выполнено, NOT_COMPLETED -не выполнено);
     * taskName - описание
     * status - меняется на UPDATED
     * return TaskResponse
     */
    public TaskResponse updateTaskById(UpdateTaskInfoDto updateTaskInfoDto) {
        Task task = taskRepository.findTaskById(updateTaskInfoDto.id())
                .orElseThrow(() -> new BaseException(ExceptionCode.NOT_FOUND));

        TaskValidator.catchTaskNameAndStatusNull(updateTaskInfoDto).apply(task);

        task.setTaskName(updateTaskInfoDto.newTaskName() != null ?
                updateTaskInfoDto.newTaskName() : task.getTaskName());

        task.setTaskStatus(updateTaskInfoDto.status() != null ?
                updateTaskInfoDto.status() : task.getTaskStatus());
        task.setStatus(Status.UPDATED);

        return TaskResponse.builder()
                .id(taskRepository.save(task).getId())
                .taskName(taskRepository.save(task).getTaskName())
                .taskStatus(taskRepository.save(task).getTaskStatus())
                .build();
    }


    /**
     * Удаление задачи по id
     *
     * @RequestParam - id - идентификатор задачи
     * статус задачи меняется на DELETED
     * return TaskResponse
     */
    public TaskResponse deleteById(Integer id) {
        Task task = taskRepository.findTaskById(id)
                .orElseThrow(() -> new BaseException(ExceptionCode.NOT_FOUND));
        task.setStatus(Status.DELETED);
        return TaskResponse.builder()
                .id(taskRepository.save(task).getId())
                .taskName(taskRepository.save(task).getTaskName())
                .taskStatus(taskRepository.save(task).getTaskStatus())
                .build();
    }
}

