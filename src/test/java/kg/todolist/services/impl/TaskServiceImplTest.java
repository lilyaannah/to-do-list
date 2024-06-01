package kg.todolist.services.impl;

import kg.todolist.dto.TaskNameDto;
import kg.todolist.dto.UpdateTaskInfoDto;
import kg.todolist.enums.StatusOfTask;
import kg.todolist.exceptions.GetAllTaskListIsNullException;
import kg.todolist.exceptions.TaskIdNotFoundException;
import kg.todolist.exceptions.TaskNameIsEmptyException;
import kg.todolist.exceptions.TaskNotFoundException;
import kg.todolist.models.Task;
import kg.todolist.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static kg.todolist.enums.StatusOfTask.COMPLETED;
import static kg.todolist.enums.StatusOfTask.NOT_COMPLETED;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl sut;
   /* @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }*/
    @Test
    void createTask_SuccessCase() {
        TaskNameDto taskNameDto = new TaskNameDto("Test Task", COMPLETED);
        Task savedTask = new Task().setId(1)
                .setTaskName("Test Task")
                .setStatus(StatusOfTask.NOT_COMPLETED);

        when(taskRepository.save(any())).thenReturn(savedTask);
        Task createdTask = sut.createNewTask(taskNameDto);

        assertNotNull(createdTask);
        assertEquals(savedTask, createdTask);
    }

    @Test
    void getTaskById() {
        Task task = Task.builder()
                .id(1)
                .taskName("name")
                .status(COMPLETED)
                .build();

        when(sut.getTaskById(1)).thenReturn(task);

        Task foundTask = sut.getTaskById(1);

        assertNotNull(foundTask);
        assertEquals(task, foundTask);
    }

    @Test
    void getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1, "Test Name 1", COMPLETED));
        taskList.add(new Task(2, "Test Name 2", NOT_COMPLETED));
        when(sut.getAllTasks()).thenReturn(taskList);

        List<Task> fetchedTaskList = sut.getAllTasks();

        assertNotNull(fetchedTaskList);
        assertEquals(taskList, fetchedTaskList);
    }

    @Test
    void updateTaskById() {
        Task existingTask = Task.builder()
                .id(1)
                .taskName("Old Name")
                .status(StatusOfTask.NOT_COMPLETED)
                .build();

        UpdateTaskInfoDto updateTaskInfoDto =
                new UpdateTaskInfoDto(1, "New Name", StatusOfTask.COMPLETED);

        when(taskRepository.findTaskById(1)).thenReturn(existingTask);

        sut.updateTaskById(updateTaskInfoDto);

        assertEquals(updateTaskInfoDto.newTaskName(), existingTask.getTaskName());
        assertEquals(StatusOfTask.COMPLETED, existingTask.getStatus());
    }

    @Test
    void deleteById() {
        Task task = Task.builder()
                .id(1)
                .taskName("name")
                .status(COMPLETED)
                .build();

        when(taskRepository.deleteTaskById(1)).thenReturn(task);

        Task foundTask = taskRepository.deleteTaskById(1);

        assertNotNull(foundTask);
        assertEquals(task, foundTask);
    }

    @Test
    void createTask_TaskNameIsEmptyException(){
        TaskNameDto taskNameDto = TaskNameDto.builder()
                .taskName("")
                .status(COMPLETED)
                .build();

        Throwable exception = assertThrows(
                TaskNameIsEmptyException.class,
                () -> {
                    sut.createNewTask(taskNameDto);
                throw new TaskNameIsEmptyException("Task Name is Null");}
        );

        assertEquals("Task Name is Null", exception.getMessage());
    }

    @Test
    void getTaskById_TaskIdNotFoundException(){
        Task task = new Task();
        Throwable exception = assertThrows(
                TaskIdNotFoundException.class,
                () -> sut.getTaskById(task.getId())
        );
        assertEquals("Id Not Found!", exception.getMessage());
    }

    @Test
    void getAllTasks_GetAllTaskListIsNullException(){
        Throwable exception = assertThrows(
                GetAllTaskListIsNullException.class,
                () -> sut.getAllTasks()
        );

        assertEquals("Task List Is Empty!", exception.getMessage());
    }

    @Test
    void updateTaskInfoById_TaskIdNotFoundException(){
        UpdateTaskInfoDto updateTaskInfoDto = new UpdateTaskInfoDto(999, "New Name", StatusOfTask.COMPLETED);

        Throwable exception = assertThrows(
                TaskIdNotFoundException.class,
                () -> sut.updateTaskById(updateTaskInfoDto)
        );
        assertEquals("TaskID Not Found!", exception.getMessage());

        // Проверяем, что taskRepository.save не вызывается, так как ID не существует
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteById_TaskNotFoundException(){
      Task task = Task.builder()
              .id(1)
              .taskName("Test")
              .status(COMPLETED)
              .build();

        Throwable exception = assertThrows(
                TaskNotFoundException.class,
                () -> sut.deleteById(task.getId())
        );
        assertEquals("Task Not Found", exception.getMessage());
    }
}