package kg.ab.todolist.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kg.ab.todolist.commons.enums.StatusOfTask;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность задачи")
public class UpdateTaskInfoDto {
    @NotNull(message = "Идентификатор задачи не найден")
    @JsonProperty("id")
    @Schema(description = "Идентификатор задачи")
    private Long id;

    @JsonProperty("task_name")
    @Schema(description = "Новая задача")
    private String newTaskName;

    @Schema(description = "Статус задачи", example = "COMPLETED")
    private StatusOfTask status;
}
