package Classes.Accounts;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import Classes.Clients.Client;
import Classes.Clients.VIPClient;
import Interfaces.Interest;

public class SavingsAccount extends Account implements Interest
{
    public SavingsAccount(Client owner){
        super(owner);
    }
    
    @Override
    public void applyInterest(){
        if(lastInterestDate != null && ChronoUnit.MONTHS.between(lastInterestDate, LocalDate.now()) < 1){
            return;
        }

        double rate = 0.02;

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
