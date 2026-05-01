package Exceptions;

public class InvestmentLockException extends Exception{
    public InvestmentLockException(){
        super("Investment account is locked. Transfers allowed only after 365 days.");
    }

    public InvestmentLockException(String message){
        super(message);
    }
}