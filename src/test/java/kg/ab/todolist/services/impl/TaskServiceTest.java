package kg.ab.todolist.services.impl;

import kg.ab.todolist.commons.enums.StatusOfTask;
import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.TaskNameDto;
import kg.ab.todolist.dto.UpdateTaskInfoDto;
import kg.ab.todolist.models.Task;
import kg.ab.todolist.models.repositories.TaskRepository;
import kg.ab.todolist.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static kg.ab.todolist.commons.enums.ExceptionCode.*;
import static kg.ab.todolist.commons.enums.StatusOfTask.COMPLETED;
import static kg.ab.todolist.commons.enums.StatusOfTask.NOT_COMPLETED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService sut;
    @Test
    void createTask() {
        TaskNameDto taskNameDto = new TaskNameDto("Test task");
        Task savedTask = Task.builder()
                .taskName("Test task")
                .status(COMPLETED)
                .build();

        when(taskRepository.save(any())).thenReturn(savedTask);

        assertEquals(savedTask, sut.createNewTask(taskNameDto));
    }

    @Test
    void getTaskById() {
        Task task = Task.builder()
                .id(1)
                .taskName("name")
                .status(COMPLETED)
                .build();

        when(taskRepository.findTaskById(1)).thenReturn(task);

        Task foundTask = sut.getTaskById(1);

        assertEquals(task, foundTask);
    }

    @Test
    void getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1, "Test Name 1", COMPLETED));
        taskList.add(new Task(2, "Test Name 2", NOT_COMPLETED));
        when(taskRepository.findAll()).thenReturn(taskList);

        assertNotNull(sut.getAllTasks());
        assertEquals(taskList, sut.getAllTasks());
    }

    @Test
    void updateTaskById() {
        Task updatedT = Task.builder()
                .id(1)
                .taskName("New Name")
                .status(COMPLETED)
                .build();
        when(taskRepository.findTaskById(1)).thenReturn(new Task());
        when(taskRepository.save(any())).thenReturn(updatedT);
        Task testSut = sut.updateTaskById(
                new UpdateTaskInfoDto(1, "New Name", COMPLETED));

        assertEquals(updatedT, testSut);
    }

    @Test
    void getTaskIdNotFoundException() {
        BaseException exception = assertThrows(
                BaseException.class,
                () -> sut.getTaskById(any())
        );
        assertEquals(NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void getAllTaskListIsNullException() {
        BaseException exception = assertThrows(
                BaseException.class,
                () -> sut.getAllTasks()
        );

        assertEquals(LIST_IS_NULL.getMessage(), exception.getMessage());
    }

    @Test
    void updateTaskIdNotFoundException() {
        UpdateTaskInfoDto updateTaskInfoDto = new UpdateTaskInfoDto(999, "New Name", StatusOfTask.COMPLETED);

        BaseException exception = assertThrows(
                BaseException.class,
                () -> sut.updateTaskById(updateTaskInfoDto)
        );
        assertEquals(NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void deleteTaskNotFoundException() {
        BaseException exception = assertThrows(
                BaseException.class,
                () -> sut.deleteById(1)
        );
        assertEquals(NOT_FOUND.getMessage(), exception.getMessage());
    }
}
