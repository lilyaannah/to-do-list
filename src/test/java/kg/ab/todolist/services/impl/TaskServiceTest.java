package kg.ab.todolist.services.impl;

import kg.ab.todolist.commons.enums.StatusOfTask;
import kg.ab.todolist.commons.exceptions.BaseException;
import kg.ab.todolist.dto.response.TaskResponse;
import kg.ab.todolist.dto.request.TaskNameDto;
import kg.ab.todolist.dto.request.UpdateTaskInfoDto;
import kg.ab.todolist.models.TaskEntity;
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
import static kg.ab.todolist.commons.enums.Status.CREATED;
import static kg.ab.todolist.commons.enums.Status.UPDATED;
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
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(TaskEntity.builder()
                .id(1L)
                .taskName("Test task")
                .taskStatus(NOT_COMPLETED)
                .build());

        TaskResponse expectedTaskResponse = TaskResponse.builder()
                .id(1L)
                .taskName("Test task")
                .taskStatus(NOT_COMPLETED)
                .build();

        TaskResponse actualTaskResponse = sut.createNewTask(TaskNameDto.builder().taskName("New name").build());

        assertEquals(expectedTaskResponse, actualTaskResponse);
    }

    @Test
    void getTaskById() {
        TaskEntity task = TaskEntity.builder()
                .id(1L)
                .taskName("name")
                .taskStatus(COMPLETED)
                .build();

        when(taskRepository.findByIdAndStatusNotDeleted(1L)).thenReturn(Optional.of(task));

        TaskResponse foundTask = sut.getTaskById(1L);

        TaskResponse expectedTask = TaskResponse.builder()
                .id(1L)
                .taskName("name")
                .taskStatus(COMPLETED)
                .build();

        assertEquals(expectedTask, foundTask);
    }

    @Test
    void getAllTasks() {
        List<TaskEntity> taskList = new ArrayList<>();
        taskList.add(new TaskEntity(1L, "Test Name 1", COMPLETED, CREATED));
        taskList.add(new TaskEntity(2L, "Test Name 2", NOT_COMPLETED, UPDATED));
        when(taskRepository.findAllStatusNotDeleted()).thenReturn(taskList);

        List<TaskResponse> responseTaskList = new ArrayList<>();
        responseTaskList.add(TaskResponse.builder().id(1L).taskName("Test Name 1").taskStatus(COMPLETED).build());
        responseTaskList.add(TaskResponse.builder().id(2L).taskName("Test Name 2").taskStatus(NOT_COMPLETED).build());

        List<TaskResponse> actual = sut.getAllTasks();
        assertNotNull(actual);
        assertEquals(actual, responseTaskList);
    }

    @Test
    void updateTaskById() {
        TaskResponse updatedT = TaskResponse.builder()
                .id(1L)
                .taskName("New name")
                .taskStatus(COMPLETED)
                .build();

        TaskEntity task = TaskEntity.builder()
                .id(1L)
                .taskName("New name")
                .taskStatus(COMPLETED)
                .build();

        when(taskRepository.findByIdAndStatusNotDeleted(1L)).thenReturn(Optional.of(new TaskEntity()));
        when(taskRepository.save(any())).thenReturn(task);

        TaskResponse testSut = sut.updateTaskById(
                new UpdateTaskInfoDto(1L, "New Name", COMPLETED));

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
        UpdateTaskInfoDto updateTaskInfoDto = new UpdateTaskInfoDto(any(), "New Name", StatusOfTask.COMPLETED);

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
                () -> sut.deleteById(1L)
        );
        assertEquals(NOT_FOUND.getMessage(), exception.getMessage());
    }
}
