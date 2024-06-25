package kg.ab.todolist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.ab.todolist.commons.enums.StatusOfTask;
import lombok.*;

@Data
@Builder
public class TaskResponse {
    @Schema(description = "Идентификтор задачи")
    private Integer id;

    @Schema(description = "Описание задачи")
    private String taskName;

    @Schema(description = "Статус задачи")
    private StatusOfTask taskStatus;
}
