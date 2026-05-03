package Classes.Accounts;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import Classes.Clients.Client;
import Classes.Clients.StudentClient;
import Classes.Clients.VIPClient;
import Interfaces.Maintainable;

public class ChequeingAccount extends Account implements Maintainable
{
    public ChequeingAccount(Client owner){
        super(owner);
    }
    
    @Override
    public void applyMonthlyFee(){
        if(lastFeeDate != null && ChronoUnit.MONTHS.between(lastFeeDate, LocalDate.now()) < 1){
            return;
        }

        double fee = (owner instanceof StudentClient || owner instanceof VIPClient) ? 0 : 10;
        balance -= fee;
        lastFeeDate = LocalDate.now();
    }

    public boolean isFeeDue(){
        return lastFeeDate == null || ChronoUnit.MONTHS.between(lastFeeDate, LocalDate.now()) >= 1;
    }
}
