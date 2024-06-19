package kg.ab.todolist.dto.response;

import kg.ab.todolist.commons.enums.StatusOfTask;
import lombok.*;

@Data
@Builder
public class TaskResponse {
    private Integer id;
    private String taskName;
    private StatusOfTask taskStatus;
}
