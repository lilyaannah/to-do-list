package kg.ab.todolist.models.repositories;

import kg.ab.todolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findTaskById(Integer id);
}
