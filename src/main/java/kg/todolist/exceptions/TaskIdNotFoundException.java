package kg.todolist.exceptions;

public class TaskIdNotFoundException extends RuntimeException{
    public TaskIdNotFoundException(String message){ super(message);}
}
