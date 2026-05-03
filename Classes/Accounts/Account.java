package Classes.Accounts;

import java.time.LocalDate;
import java.util.ArrayList;
import Exceptions.InsufficientFundsException;
import Classes.Transaction;
import Classes.Clients.Client;

public abstract class Account 
{
    public String accountNum;
    public transient Client owner;
    public String ownerID;
    public double balance;
    public LocalDate openingDate;
    public ArrayList<Transaction> transactions = new ArrayList<>();
    public static int counter = 0;
    public LocalDate lastInterestDate;
    public LocalDate lastFeeDate;
    

    public Account(Client owner){
        this.accountNum = ++counter + "";
        this.owner = owner;
        this.ownerID = owner.clientID;
        this.balance = 0;
        this.openingDate = LocalDate.now();
    }
    
    public void deposit(double amount){
        if(amount > 0){
            balance += amount;
            transactions.add(new Transaction("deposit", amount));
        }
        else{
            //Replace with JavaFX
            System.out.println("Invalid amount");
        }
    }

    public void withdraw(double amount){
        try{
            if(amount > balance){
                throw new InsufficientFundsException();
            }
        } 
        catch(InsufficientFundsException e){
            System.out.println(e);
        }

        balance -= amount;
        transactions.add(new Transaction("withdraw", amount));
    }

    public double getBalance(){
        return balance;
    }
}