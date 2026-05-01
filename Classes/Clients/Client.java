package Classes.Clients;

import java.util.ArrayList;
import Classes.Accounts.Account;

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

    /* void addAccount(Account acc){
        IF acc is Savings OR Investment
            CHECK if client has ChequeingAccount
            IF NOT
                THROW MissingChequeingAccountException
        ADD acc to accounts list  
    } */
    
    public boolean login(String id, String password){
        if(clientID.equals(id) && this.password.equals(password)){
            return true;
        }
        
        //Replace with JavaFX
        System.out.println("Id or password is incorrect.");
        return false;
    }
}
