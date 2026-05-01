package Classes.Accounts;

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
        double fee;
        
        if(owner instanceof StudentClient || owner instanceof VIPClient){
            fee = 0;
        }
        else{
            fee = 10;
        }

        balance -= fee;
    }
}
