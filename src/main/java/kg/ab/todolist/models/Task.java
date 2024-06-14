package kg.ab.todolist.models;

import jakarta.persistence.*;
import kg.ab.todolist.commons.enums.StatusOfTask;
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOfTask status;


}
