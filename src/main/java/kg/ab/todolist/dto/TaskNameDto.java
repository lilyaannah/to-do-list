package kg.ab.todolist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kg.ab.todolist.commons.enums.StatusOfTask;
import lombok.Builder;
import lombok.NonNull;

@Builder
@Schema(description = "Сущность задачи")
public record TaskNameDto(
        @JsonProperty("task_name")
        @NotBlank(message = "not blank is working")
        @NotNull(message = "notnull is working")
        @Schema(description = "Название задачи")
        String taskName) {
}
