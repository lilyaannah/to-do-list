package kg.ab.todolist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import kg.ab.todolist.commons.enums.StatusOfTask;

@Schema(description = "Сущность задачи")
public record UpdateTaskInfoDto(@JsonProperty("id") @Schema(description = "Идентификатор") Integer id,
                                @JsonProperty("task_name") @Schema(description = "Новая задача") String newTaskName,
                                @Schema(description = "Статус задачи", example = "COMPLETED") StatusOfTask status) {
}
