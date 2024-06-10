package kg.todolist.services.impl;

import kg.todolist.commons.enums.ExceptionCode;
import kg.todolist.commons.enums.StatusOfTask;
import kg.todolist.commons.exceptions.BaseException;
import kg.todolist.dto.TaskNameDto;
import kg.todolist.dto.UpdateTaskInfoDto;
import kg.todolist.models.Task;
import kg.todolist.models.repositories.TaskRepository;
import kg.todolist.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static kg.todolist.commons.enums.ExceptionCode.*;
import static kg.todolist.commons.enums.StatusOfTask.COMPLETED;
import static kg.todolist.commons.enums.StatusOfTask.NOT_COMPLETED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService sut;
    @Test
    void createTask() {
        TaskNameDto taskNameDto = new TaskNameDto("Test task", COMPLETED);
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

        List<Task> fetchedTaskList = sut.getAllTasks();

        assertNotNull(fetchedTaskList);
        assertEquals(taskList, fetchedTaskList);
    }

//    @Test
//    void updateTaskById() {
//        Task newTask = Task.builder()
//                .id(1)
//                .taskName("New Name")
//                .status(StatusOfTask.NOT_COMPLETED)
//                .build();
//
//        UpdateTaskInfoDto updateTaskInfoDto =
//                new UpdateTaskInfoDto(1, "New Name", StatusOfTask.COMPLETED);
//
////        when(taskRepository.findTaskById(updateTaskInfoDto.id())).thenReturn();
////        when(taskRepository.save(any())).thenReturn(savedTask);
//        sut.updateTaskById(updateTaskInfoDto);
//
//        assertEquals(updateTaskInfoDto.newTaskName(), newTask.getTaskName());
//        assertEquals(StatusOfTask.COMPLETED, newTask.getStatus());
//    }

    @Test
    void taskNameIsEmptyException() {
        TaskNameDto taskNameDto = TaskNameDto.builder()
                .taskName("")
                .status(COMPLETED)
                .build();

        BaseException exception = assertThrows(
                BaseException.class,
                () -> sut.createNewTask(taskNameDto)
        );

        assertEquals(TASK_NAME_IS_NULL.getMessage(), exception.getMessage());
    }

    @Test
    void getTaskIdNotFoundException() {
        BaseException exception = assertThrows(
                BaseException.class,
                () -> sut.getTaskById(any())
        );
        assertEquals(TASK_ID_NOT_FOUND.getMessage(), exception.getMessage());
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
        assertEquals(TASK_ID_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void deleteTaskNotFoundException() {
        BaseException exception = assertThrows(
                BaseException.class,
                () -> sut.deleteById(1)
        );
        assertEquals(TASK_ID_NOT_FOUND.getMessage(), exception.getMessage());
    }
}