package kg.ab.todolist.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "Сущность задачи")
public record TaskNameDto(
        @JsonProperty("task_name")
        @NotBlank(message = "Задача не может быть пустым")
        @NotNull(message = "Задача не найдена")
        @Schema(description = "Название задачи")
        String taskName) {
}
