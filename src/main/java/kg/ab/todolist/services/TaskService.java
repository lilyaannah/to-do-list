package kg.ab.todolist.services;

import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.request.TaskNameDto;
import kg.ab.todolist.dto.response.TaskResponse;
import kg.ab.todolist.models.Task;
import kg.ab.todolist.models.repositories.TaskRepository;
import kg.ab.todolist.services.validation.TaskValidator;
import kg.ab.todolist.dto.request.UpdateTaskInfoDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kg.ab.todolist.commons.enums.ExceptionCode.LIST_IS_NULL;
import static kg.ab.todolist.commons.enums.ExceptionCode.NOT_FOUND;
import static kg.ab.todolist.commons.enums.Status.*;
import static kg.ab.todolist.commons.enums.StatusOfTask.NOT_COMPLETED;

@Service
@RequiredArgsConstructor
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
                .taskName(taskNameDto.getTaskName())
                .taskStatus(NOT_COMPLETED)
                .status(CREATED)
                .build());

        return TaskResponse.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .taskStatus(NOT_COMPLETED)
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
                .filter(sm -> sm.getStatus() != DELETED)
                .orElseThrow(() -> new BaseException(NOT_FOUND));
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
            throw new BaseException(LIST_IS_NULL);
        }
        return taskList.stream()
                .filter(task -> task.getStatus() != DELETED)
                .map(task -> TaskResponse.builder()
                        .id(task.getId())
                        .taskName(task.getTaskName())
                        .taskStatus(task.getTaskStatus()).build()).toList();
    }

    /**
     * @RequestBody UpdateTaskInfoDto - taskStatus - статус задачи (COMPLETED-выполнено, NOT_COMPLETED -не выполнено);
     * taskName - описание
     * status - меняется на UPDATED
     * return TaskResponse
     */
    public TaskResponse updateTaskById(UpdateTaskInfoDto updateTaskInfoDto) {
        Task task = taskRepository.findTaskById(updateTaskInfoDto.getId())
                .orElseThrow(() -> new BaseException(NOT_FOUND));

        TaskValidator.catchTaskNameAndStatusNull(updateTaskInfoDto).apply(task);

        task.setTaskName(updateTaskInfoDto.getNewTaskName() != null ?
                updateTaskInfoDto.getNewTaskName() : task.getTaskName());

        task.setTaskStatus(updateTaskInfoDto.getStatus() != null ?
                updateTaskInfoDto.getStatus() : task.getTaskStatus());
        task.setStatus(UPDATED);

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
                .filter(t -> t.getStatus() != DELETED)
                .orElseThrow(() -> new BaseException(NOT_FOUND));
        task.setStatus(DELETED);
        return TaskResponse.builder()
                .id(taskRepository.save(task).getId())
                .taskName(taskRepository.save(task).getTaskName())
                .taskStatus(taskRepository.save(task).getTaskStatus())
                .build();
    }
}

