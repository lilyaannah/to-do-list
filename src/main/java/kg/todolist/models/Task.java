package kg.todolist.models;

import jakarta.persistence.*;
import kg.todolist.commons.enums.StatusOfTask;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String taskName;

    @Enumerated(EnumType.STRING)
    StatusOfTask status;
}
