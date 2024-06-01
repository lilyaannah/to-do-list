package kg.todolist.models;

import jakarta.persistence.*;
import kg.todolist.enums.StatusOfTask;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String taskName;

    @Enumerated(EnumType.STRING)
    StatusOfTask status;
}
