package kg.ab.todolist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import kg.ab.todolist.commons.enums.StatusOfTask;
import lombok.Builder;

@Builder
@Schema(description = "Сущность задачи")
public record TaskNameDto(@JsonProperty("task_name") @Schema(description = "Название задачи")  String taskName,
                          @JsonProperty("status") @Schema(description = "Статус задачи", example = "COMPLETED") StatusOfTask status) {

}
