package kg.ab.todolist.dto;

import kg.ab.todolist.commons.enums.StatusOfTask;
import lombok.*;

@Builder
public record TaskResponse(Integer id,
        String taskName,
        StatusOfTask taskStatus) {
}
