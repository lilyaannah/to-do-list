package kg.ab.todolist.models.repositories;

import kg.ab.todolist.models.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("SELECT t FROM TaskEntity t WHERE t.id = :id AND t.status != 'DELETED'")
    Optional<TaskEntity> findByIdAndStatusNotDeleted(@Param("id") Long id);

    @Query("SELECT t FROM TaskEntity t WHERE t.status != 'DELETED'")
    List<TaskEntity> findAllStatusNotDeleted();
}
