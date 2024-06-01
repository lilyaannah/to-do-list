package kg.todolist.exceptions;

public class TaskNameAndStatusNullException extends RuntimeException{
    public TaskNameAndStatusNullException(String message){
        super(message);
    }
}
