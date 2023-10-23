package Exceptions;

public class EmailAlreadyUsedException extends Exception{

    public EmailAlreadyUsedException(String msg){
        super(msg);
    }
}
