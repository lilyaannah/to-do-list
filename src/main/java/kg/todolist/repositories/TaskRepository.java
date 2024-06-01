package kg.todolist.repositories;

import kg.todolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Task findTaskById(Integer id);
    void deleteById(Integer id);
    Task deleteTaskById(Integer id);

}
