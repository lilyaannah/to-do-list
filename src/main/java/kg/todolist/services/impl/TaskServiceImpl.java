package kg.todolist.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kg.todolist.dto.TaskNameDto;
import kg.todolist.dto.UpdateTaskInfoDto;
import kg.todolist.enums.StatusOfTask;
import kg.todolist.exceptions.*;
import kg.todolist.models.Task;
import kg.todolist.repositories.TaskRepository;
import kg.todolist.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private EntityManager entityManager;

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createNewTask(TaskNameDto taskNameDto) {
        if(taskNameDto.taskName()==null){
            throw new TaskNameIsEmptyException("TaskName Not Found! ");
        }
        Task task = new Task().setTaskName(taskNameDto.taskName()).setStatus(StatusOfTask.NOT_COMPLETED);
        task=taskRepository.save(task);
        return task;
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task =taskRepository.findTaskById(id);
        if(task == null){
            throw new TaskIdNotFoundException("Id Not Found!");
        }
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        if(taskList.isEmpty()){
            throw new GetAllTaskListIsNullException("Task List Is Empty!");
        }
        return taskList;
    }

    @Override
    public void updateTaskById(UpdateTaskInfoDto updateTaskInfoDto) {
        if(taskRepository.findTaskById(updateTaskInfoDto.id())==null){
            throw new TaskIdNotFoundException("TaskID Not Found!");
        }
        Task task = taskRepository.findTaskById(updateTaskInfoDto.id());
        if (updateTaskInfoDto.newTaskName() == null && updateTaskInfoDto.status() != null) {
            task.setStatus(updateTaskInfoDto.status());
        } else if (updateTaskInfoDto.status() == null && updateTaskInfoDto.newTaskName() != null) {
            task.setTaskName(updateTaskInfoDto.newTaskName());
        } else if (updateTaskInfoDto.newTaskName() != null && updateTaskInfoDto.status() != null) {
            task.setStatus(updateTaskInfoDto.status());
            task.setTaskName(updateTaskInfoDto.newTaskName());
        }else {
            throw new TaskNameAndStatusNullException("TaskName And TaskStatus is NULL!");
        }

        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Task task =taskRepository.findTaskById(id);
        if(task==null){
            throw new TaskNotFoundException("Task Not Found");
        }
        taskRepository.deleteById(task.getId());
    }


}

