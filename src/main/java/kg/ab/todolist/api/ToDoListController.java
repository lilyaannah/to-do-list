package kg.ab.todolist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.ab.todolist.commons.enums.ExceptionCode;
import kg.ab.todolist.dto.TaskNameDto;
import kg.ab.todolist.dto.UpdateTaskInfoDto;
import kg.ab.todolist.models.Task;
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
    public ResponseEntity<String> createTask(@RequestBody @Schema(example = """
            { "taskName" : "Example Task" }""") TaskNameDto taskName) {
        taskService.createNewTask(taskName);
        return new ResponseEntity<>("Task create successfully", ExceptionCode.SUCCESS.getStatus());
    }

    @Operation(
            summary = "Получение новой задачи по id",
            description = "Позволяет получить задачу по id"
    )
    @GetMapping("/getTaskById")
    public ResponseEntity<Task> getTaskById(@RequestParam
                                            @Parameter(description = "Идентификатор задачи") Integer id) {
        return new ResponseEntity<>(taskService.getTaskById(id), ExceptionCode.SUCCESS.getStatus());
    }

    @Operation(
            summary = "Получение всех задач",
            description = "Позволяет получить все задачи с бд"
    )
    @GetMapping("/getAllTasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), ExceptionCode.SUCCESS.getStatus());
    }


    @Operation(
            summary = "Обновление данных задачи",
            description = "Позволяет обновлять определенные данные задачи"
    )
    @PutMapping()
    public ResponseEntity<String> updateTask(@RequestBody @Schema(example = """
            {"id" : "id",  "taskName" : "Example Task", "status" : "COMPLETED"}""")
                                             UpdateTaskInfoDto updateTaskInfoDto) {
        taskService.updateTaskById(updateTaskInfoDto);
        return new  ResponseEntity<>("Task updated successfully", ExceptionCode.SUCCESS.getStatus());
    }

    @Operation(
            summary = "Удаление задачи",
            description = "Позволяет удалять задачи с бд"
    )
    @DeleteMapping()
    public ResponseEntity<String> deleteTask(@RequestParam @Schema(example = """
            {"id" : "id" }""") @Parameter(description = "Идентификатор задачи") Integer id) {
        taskService.deleteById(id);
        return  new ResponseEntity<>("Task deleted successfully", ExceptionCode.SUCCESS.getStatus());
    }
}