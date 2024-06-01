package kg.todolist.exceptions;

public class TaskNameIsEmptyException extends RuntimeException{
    public TaskNameIsEmptyException(String message){
        super(message);
    }
}
