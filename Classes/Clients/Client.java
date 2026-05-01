package Classes.Clients;

import java.util.ArrayList;
import Classes.Accounts.Account;
import Classes.Accounts.ChequeingAccount;
import Classes.Accounts.InvestmentAccount;
import Classes.Accounts.SavingsAccount;
import Exceptions.MissingChequeingAccountException;

public abstract class Client 
{
    public String clientID;
    public String name;
    public String password;
    public ArrayList<Account> accounts = new ArrayList<>();
    public static int counter = 0;

    public Client(String name, String password){
        this.clientID = ++counter + "";
        this.name = name;
        this.password = password;
    }

    void addAccount(Account acc){
        if(acc instanceof SavingsAccount || acc instanceof InvestmentAccount){
            boolean hasChequeing = false;

            for(Account a : accounts){
                if(a instanceof ChequeingAccount){
                    hasChequeing = true;
                    break;
                }
            }
            
            try{
                if(!hasChequeing){
                    throw new MissingChequeingAccountException();
                }  
            } 
            catch(MissingChequeingAccountException e){
                System.out.println(e);
            }
        }
        
        accounts.add(acc); 
    }

    public void transfer(Account from, Account to, double amount){
        from.withdraw(amount);
        to.deposit(amount);
    }
    
    public boolean login(String id, String password){
        if(clientID.equals(id) && this.password.equals(password)){
            return true;
        }
        
        //Replace with JavaFX
        System.out.println("Id or password is incorrect.");
        return false;
    }
}
