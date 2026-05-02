package Classes.Accounts;

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
        double rate = 0.02;
        
        if(owner instanceof VIPClient){
            rate += 0.01;
        }

        balance += balance * rate;
    }
}
