package kg.ab.todolist.commons.exceptions.response;

public class WrongRequestException extends RuntimeException{
    public WrongRequestException(String message){
        super(message);
    }
}
