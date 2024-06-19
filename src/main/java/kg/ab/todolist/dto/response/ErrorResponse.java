package kg.ab.todolist.dto.response;

import kg.ab.todolist.commons.enums.ExceptionCode;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private ExceptionCode code;
    private String message;
}
