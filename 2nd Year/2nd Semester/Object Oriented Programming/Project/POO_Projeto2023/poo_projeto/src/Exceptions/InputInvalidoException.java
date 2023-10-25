package Exceptions;

public class InputInvalidoException extends Exception{
    public InputInvalidoException(){
        super("O input introduzido não é valido!");
    }

    public InputInvalidoException(String msg){
        super(msg);
    }
}
