package kg.todolist.services;

import kg.todolist.dto.TaskNameDto;
import kg.todolist.dto.UpdateTaskInfoDto;
import kg.todolist.models.Task;

import java.util.List;

public interface TaskService {
    Task createNewTask(TaskNameDto taskNameDto);

    Task getTaskById(Integer id);

    List<Task> getAllTasks();

    void updateTaskById(UpdateTaskInfoDto updateTaskInfoDto);

    void deleteById(Integer id);
}
