package Classes.Clients;

import java.time.LocalDate;
import java.util.ArrayList;
import Classes.Accounts.Account;
import Classes.Accounts.ChequeingAccount;
import Classes.Accounts.InvestmentAccount;
import Classes.Accounts.SavingsAccount;
import Exceptions.MissingChequeingAccountException;
import java.time.temporal.ChronoUnit;


public abstract class Client 
{
    public String clientID;
    public String name;
    public String password;
    public String type;
    public ArrayList<Account> accounts = new ArrayList<>();
    public static int counter = 0;
    public LocalDate lastMaintenanceDate;

    public Client(String name, String password){
        this.clientID = ++counter + "";
        this.name = name;
        this.password = password;
        this.type = this.getClass().getSimpleName();
    }

    public void addAccount(Account acc) throws MissingChequeingAccountException{
        if(acc instanceof SavingsAccount || acc instanceof InvestmentAccount){
            boolean hasChequeing = false;

            for(Account a : accounts){
                if(a instanceof ChequeingAccount){
                    hasChequeing = true;
                    break;
                }
            }
            
            if(!hasChequeing){
                throw new MissingChequeingAccountException();
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
        
        return false;
    }

    public int applyInterestToAll(){
        int count = 0;

        for(Account acc : accounts){
            if(acc instanceof SavingsAccount && ((SavingsAccount) acc).isInterestDue()){
                ((SavingsAccount) acc).applyInterest();
                count++;
            } 
            else if(acc instanceof InvestmentAccount && ((InvestmentAccount) acc).isInterestDue()){
                ((InvestmentAccount) acc).applyInterest();
                count++;
            }
        }

        return count;
    }

    public int applyFeesToAll(){
        int count = 0;
        for(Account acc : accounts){
            if(acc instanceof ChequeingAccount){
                ChequeingAccount chq = (ChequeingAccount) acc;
                if(chq.isFeeDue()){
                    chq.applyMonthlyFee();
                    count++;
                }
            }
        }

        return count;
    }

    public boolean applyMonthlyMaintenanceIfDue(){
        if(lastMaintenanceDate == null || ChronoUnit.MONTHS.between(lastMaintenanceDate, LocalDate.now()) >= 1){
            applyInterestToAll();
            applyFeesToAll();
            lastMaintenanceDate = LocalDate.now();
            return true;
        }
        
        return false;
    }

    public String getClientID(){
        return clientID;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }
}
