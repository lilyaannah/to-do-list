package kg.ab.todolist.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.ab.todolist.dto.request.TaskNameDto;
import kg.ab.todolist.dto.response.TaskResponse;
import kg.ab.todolist.dto.request.UpdateTaskInfoDto;
import kg.ab.todolist.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.ab.todolist.commons.statics.EndPoints.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(TASK_API)
@Tag(name = "To-Do-List Контроллер ", description = "Взаимодействие с сервисом")
public class ToDoListController {
    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Создание новой задачи", description = "Позволяет создавать новые задачи")
    public ResponseEntity<TaskResponse> createTask(@Valid
                                                   @RequestBody
                                                   @Schema(description = "Название задачи") TaskNameDto taskNameDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createNewTask(taskNameDto));
    }

    @GetMapping(value = "/taskId")
    @Operation(summary = "Получение новой задачи по id", description = "Позволяет получить задачу по id")
    public ResponseEntity<TaskResponse> getTaskById(@RequestParam
                                                    @NotNull(message = "Id not null")
                                                    @Parameter(description = "Идентификатор задачи")
                                                    Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех задач", description = "Позволяет получить все задачи с бд")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getAllTasks());
    }

    @PatchMapping
    @Operation(summary = "Обновление данных задачи", description = "Позволяет обновлять определенные данные задачи")
    public ResponseEntity<TaskResponse> updateTask(@Valid
                                                   @RequestBody
                                                   @Schema(example = """
                                                           {
                                                           "id" : "id",
                                                           "taskName" : "Example Task",
                                                           "status" : "COMPLETED"
                                                           }
                                                           """) UpdateTaskInfoDto updateTaskInfoDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.updateTaskById(updateTaskInfoDto));
    }

    @DeleteMapping
    @Operation(summary = "Удаление задачи", description = "Позволяет удалять задачи с бд")
    public ResponseEntity<TaskResponse> deleteTask(@RequestParam
                                                   @NotNull(message = "Id not null")
                                                   @Schema(example = """
                                                           {
                                                           "id" : "id"
                                                           }
                                                           """)
                                                   @Parameter(description = "Идентификатор задачи") Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.deleteById(id));
    }
}
