package kg.ab.todolist.models;

import jakarta.persistence.*;
import kg.ab.todolist.commons.enums.StatusOfTask;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String taskName;

    @Enumerated(EnumType.STRING)
    private StatusOfTask status;


}
