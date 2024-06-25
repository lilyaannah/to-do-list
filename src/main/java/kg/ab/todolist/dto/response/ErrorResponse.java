package kg.ab.todolist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.ab.todolist.commons.enums.ExceptionCode;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @Schema(description = "Код статуса исключения")
    private ExceptionCode code;

    @Schema(description = "Сообщение ошибки")
    private String message;
}
