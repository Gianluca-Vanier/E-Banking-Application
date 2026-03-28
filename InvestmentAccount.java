/* public class InvestmentAccount extends Account 
{
    void transfertoChequeing(amount){
        IF currentDate - openingDate < 365 days
            THROW InvestmentLockException

        IF amount <= balance
            balance -= amount
            chequeing.balance += amount
        ELSE
            THROW InsufficientFundsException
    }
    
    @Override
    void withdraw(){
        DISPLAY "Direct withdrawal not allowed"
    }

    void applyInterest(){
        interest = balance * 0.05

        IF client is VIPClient
            interest += balance * 0.01

        balance += interest
    }
} */
