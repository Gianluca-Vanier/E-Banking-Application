package Classes.Accounts;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import Classes.Clients.Client;
import Classes.Clients.VIPClient;
import Exceptions.InsufficientFundsException;
import Exceptions.InvestmentLockException;
import Interfaces.Interest;

public class InvestmentAccount extends Account implements Interest
{
    public InvestmentAccount(Client owner){
        super(owner);
    }
    
    public void transfertoChequeing(Account chequeing, double amount){
        try{
            if(LocalDate.now().isBefore(openingDate.plusDays(365))){
                throw new InvestmentLockException();
            }

            if(amount > balance){
                throw new InsufficientFundsException();
            }
        } 
        catch(InvestmentLockException e){
            System.out.println(e);
        }
        catch(InsufficientFundsException e){
            System.out.println(e);
        }

        balance -= amount;
        chequeing.deposit(amount);
    }
    
    @Override
    public void withdraw(double amount){
        throw new UnsupportedOperationException("Direct withdrawl now allowed.");
    }

    @Override
    public void applyInterest(){  
        if(lastInterestDate != null && ChronoUnit.MONTHS.between(lastInterestDate, LocalDate.now()) < 1){
            return;
        }
        
        double rate = 0.05;

        if(owner instanceof VIPClient){
            rate += 0.01;
        }

        balance += balance * rate;
        lastInterestDate = LocalDate.now();
    }

    public boolean isInterestDue() {
        return lastInterestDate == null || ChronoUnit.MONTHS.between(lastInterestDate, LocalDate.now()) >= 1;
    }
}
