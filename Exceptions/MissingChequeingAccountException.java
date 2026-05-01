package Exceptions;

public class MissingChequeingAccountException extends Exception{
    public MissingChequeingAccountException(){
        super("A chequeing account is required before opening an account.");
    }

    public MissingChequeingAccountException(String message){
        super(message);
    }
}