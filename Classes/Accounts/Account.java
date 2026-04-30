package Classes.Accounts;

import java.time.LocalDate;

import Classes.Transaction;

public abstract class Account 
{
    public String accountNum;
    public double balance;
    public LocalDate openingDate;
    public static int counter = 0;

    public Account(){
        this.accountNum = ++counter + "";
        this.balance = 0;
        this.openingDate = LocalDate.now();
    }
    
    public void deposit(double amount){
        if(amount > 0){
            balance += amount;
            Transaction deposit = new Transaction("deposit", amount);
        }
        else{
            //Replace with JavaFX
            System.out.println("Invalid amount");
        }
    }

    public void withdraw(double amount){
        if(amount <= balance){
            balance -= amount;
            Transaction withdraw = new Transaction("withdraw", amount);
        }
        else{
            throw new InsufficientFundsException();
        }
    }

    public double getBalance(){
        return balance;
    }
}