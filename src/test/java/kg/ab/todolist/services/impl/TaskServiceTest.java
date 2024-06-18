package kg.ab.todolist.services.impl;

import kg.ab.todolist.commons.enums.Status;
import kg.ab.todolist.commons.enums.StatusOfTask;
import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.TaskResponse;
import kg.ab.todolist.dto.request.TaskNameDto;
import kg.ab.todolist.dto.request.UpdateTaskInfoDto;
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
import java.util.Optional;

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
        when(taskRepository.save(any(Task.class))).thenReturn(Task.builder()
                .id(1)
                .taskName("Test task")
                .taskStatus(StatusOfTask.NOT_COMPLETED)
                .build());

        TaskResponse expectedTaskResponse = TaskResponse.builder()
                .id(1)
                .taskName("Test task")
                .taskStatus(StatusOfTask.NOT_COMPLETED)
                .build();

        TaskResponse actualTaskResponse = sut.createNewTask(new TaskNameDto("Test task"));

        assertEquals(expectedTaskResponse, actualTaskResponse);
    }

    @Test
    void getTaskById() {
        Task task = Task.builder()
                .id(1)
                .taskName("name")
                .taskStatus(StatusOfTask.COMPLETED)
                .build();

        when(taskRepository.findTaskById(1)).thenReturn(Optional.of(task));

        TaskResponse foundTask = sut.getTaskById(1);

        TaskResponse expectedTask = TaskResponse.builder()
                .id(1)
                .taskName("name")
                .taskStatus(StatusOfTask.COMPLETED)
                .build();

        assertEquals(expectedTask, foundTask);
    }

    @Test
    void getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1, "Test Name 1", COMPLETED, Status.CREATED));
        taskList.add(new Task(2, "Test Name 2", NOT_COMPLETED, Status.UPDATED));
        when(taskRepository.findAll()).thenReturn(taskList);

        List<TaskResponse> responseTaskList = new ArrayList<>();
        responseTaskList.add(new TaskResponse(1, "Test Name 1", COMPLETED));
        responseTaskList.add(new TaskResponse(2, "Test Name 2", NOT_COMPLETED));

        List<TaskResponse> actual = sut.getAllTasks();
        assertNotNull(actual);
        assertEquals(actual, responseTaskList);
    }

    @Test
    void updateTaskById() {
        TaskResponse updatedT = TaskResponse.builder()
                .id(1)
                .taskName("New name")
                .taskStatus(COMPLETED)
                .build();

        Task task = Task.builder()
                .id(1)
                .taskName("New name")
                .taskStatus(COMPLETED)
                .build();

        when(taskRepository.findTaskById(1)).thenReturn(Optional.of(new Task()));
        when(taskRepository.save(any())).thenReturn(task);

        TaskResponse testSut = sut.updateTaskById(
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
