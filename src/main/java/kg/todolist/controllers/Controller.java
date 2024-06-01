package kg.todolist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import kg.todolist.dto.TaskNameDto;
import kg.todolist.dto.UpdateTaskInfoDto;
import kg.todolist.models.Task;
import kg.todolist.services.TaskService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="To-Do-List Контроллер ", description="Взаимодействие с сервисом")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api")
public class Controller {

    @Autowired
    TaskService taskService;

    public Controller(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Создание новой задачи",
            description = "Позволяет создавать новые задачи"
    )
    @PostMapping("/create-task")
    public ResponseEntity<String> createTask(@RequestBody TaskNameDto taskName){
        taskService.createNewTask(taskName);
        return new ResponseEntity<>("Task create successfully", HttpStatus.OK);
    }

    @Operation(
            summary = "Получение новой задачи по id",
            description = "Позволяет получить задачу по id"
    )
    @GetMapping("/get-task-by-id{id}")
    public ResponseEntity<Task> getTaskById(@RequestParam @Parameter(description = "Идентификатор задачи") Integer id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(
            summary = "Получение всех задач",
            description = "Позволяет получить все задачи с бд"
    )
    @GetMapping("/get-all-tasks")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @Operation(
            summary = "Обновление данных задачи",
            description = "Позволяет обновлять определенные данные задачи"
    )
    @PutMapping("/update-task")
    public ResponseEntity<String> updateTask(@RequestBody UpdateTaskInfoDto updateTaskInfoDto) {
        taskService.updateTaskById(updateTaskInfoDto);
        return ResponseEntity.ok("Task updated successfully");
    }

    @Operation(
            summary = "Удаление задачи",
            description = "Позволяет удалять задачи с бд"
    )
    @DeleteMapping("/delete-task")
    @Transactional
    public ResponseEntity<String> deleteTask(@RequestParam(value="id") @Parameter(description = "Идентификатор задачи") Integer id) {
        taskService.deleteById(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
}
