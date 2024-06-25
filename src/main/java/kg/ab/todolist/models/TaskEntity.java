package kg.ab.todolist.models;

import jakarta.persistence.*;
import kg.ab.todolist.commons.enums.Status;
import kg.ab.todolist.commons.enums.StatusOfTask;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Enumerated(EnumType.STRING)
    private StatusOfTask taskStatus;

    @Enumerated(EnumType.STRING)
    private Status status;
}
