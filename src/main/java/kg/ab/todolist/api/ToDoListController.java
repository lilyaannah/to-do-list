package kg.ab.todolist.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.ab.todolist.commons.enums.ExceptionCode;
import kg.ab.todolist.dto.request.TaskNameDto;
import kg.ab.todolist.dto.TaskResponse;
import kg.ab.todolist.dto.request.UpdateTaskInfoDto;
import kg.ab.todolist.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "To-Do-List Контроллер ", description = "Взаимодействие с сервисом")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ToDoListController {
    private final TaskService taskService;

    @Operation(
            summary = "Создание новой задачи",
            description = "Позволяет создавать новые задачи"
    )
    @PostMapping()
    public ResponseEntity<TaskResponse> createTask(
            @Valid
            @RequestBody
            @Schema(description = "Название задачи") TaskNameDto taskNameDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createNewTask(taskNameDto));
    }

    @Operation(
            summary = "Получение новой задачи по id",
            description = "Позволяет получить задачу по id"
    )
    @GetMapping("/getTaskById")
    public ResponseEntity<TaskResponse> getTaskById(@NotNull
                                                    @Valid
                                                    @RequestParam
                                                    @Parameter(description = "Идентификатор задачи") Integer id) {
        return new ResponseEntity<>(taskService.getTaskById(id), ExceptionCode.SUCCESS.getStatus());
    }

    @Operation(
            summary = "Получение всех задач",
            description = "Позволяет получить все задачи с бд"
    )
    @GetMapping("/getAllTasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), ExceptionCode.SUCCESS.getStatus());
    }

    @Operation(
            summary = "Обновление данных задачи",
            description = "Позволяет обновлять определенные данные задачи"
    )
    @PatchMapping()
    public ResponseEntity<TaskResponse> updateTask(@Valid
                                                   @RequestBody
                                                   @Schema(example = """
                                                           {"id" : "id",
                                                           "taskName" : "Example Task",
                                                           "status" : "COMPLETED"}""")
                                                   UpdateTaskInfoDto updateTaskInfoDto) {
        return ResponseEntity.status(ExceptionCode.SUCCESS.getStatus())
                .body(taskService.updateTaskById(updateTaskInfoDto));
    }

    @Operation(
            summary = "Удаление задачи",
            description = "Позволяет удалять задачи с бд"
    )
    @DeleteMapping()
    public ResponseEntity<TaskResponse> deleteTask(@RequestParam
                                                   @Schema(example = """
                                                           { "id" : "id" }""")
                                                   @Parameter(description = "Идентификатор задачи") Integer id) {
        return ResponseEntity.status(ExceptionCode.SUCCESS.getStatus())
                .body(taskService.deleteById(id));
    }
}
