package kg.ab.todolist.services;

import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.request.TaskNameDto;
import kg.ab.todolist.dto.response.TaskResponse;
import kg.ab.todolist.models.TaskEntity;
import kg.ab.todolist.models.repositories.TaskRepository;
import kg.ab.todolist.services.validation.TaskValidator;
import kg.ab.todolist.dto.request.UpdateTaskInfoDto;
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
        TaskEntity task = taskRepository.save(TaskEntity.builder()
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
    public TaskResponse getTaskById(Long id) {
        TaskEntity taskEntity = taskRepository.findByIdAndStatusNotDeleted(id)
                .orElseThrow(() -> new BaseException(NOT_FOUND));
        return TaskResponse.builder()
                .id(taskEntity.getId())
                .taskName(taskEntity.getTaskName())
                .taskStatus(taskEntity.getTaskStatus())
                .build();
    }

    /**
     * Получение списка всех задач
     * кроме тех задач у которых статус состояния DELETED
     * return TaskResponse
     */
    public List<TaskResponse> getAllTasks() {
        List<TaskEntity> taskList = taskRepository.findAllStatusNotDeleted();
        if (taskList.isEmpty()) {
            throw new BaseException(LIST_IS_NULL);
        }
        return taskList.stream()
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
        TaskEntity task = taskRepository.findByIdAndStatusNotDeleted(updateTaskInfoDto.getId())
                .orElseThrow(() -> new BaseException(NOT_FOUND));
        TaskValidator.catchTaskNameAndStatusNull(updateTaskInfoDto).apply(task);

        if (updateTaskInfoDto.getNewTaskName() != null) {
            task.setTaskName(updateTaskInfoDto.getNewTaskName());
        }

        if (updateTaskInfoDto.getStatus() != null) {
            task.setTaskStatus(updateTaskInfoDto.getStatus());
        }

        task.setStatus(UPDATED);
        TaskEntity savedTask = taskRepository.save(task);
        return TaskResponse.builder()
                .id(savedTask.getId())
                .taskName(savedTask.getTaskName())
                .taskStatus(savedTask.getTaskStatus())
                .build();
    }


    /**
     * Удаление задачи по id
     *
     * @RequestParam - id - идентификатор задачи
     * статус задачи меняется на DELETED
     * return TaskResponse
     */
    public TaskResponse deleteById(Long id) {
        TaskEntity task = taskRepository.findByIdAndStatusNotDeleted(id)
                .orElseThrow(() -> new BaseException(NOT_FOUND));
        task.setStatus(DELETED);
        TaskEntity deletedTask = taskRepository.save(task);
        return TaskResponse.builder()
                .id(deletedTask.getId())
                .taskName(deletedTask.getTaskName())
                .taskStatus(deletedTask.getTaskStatus())
                .build();
    }
}

