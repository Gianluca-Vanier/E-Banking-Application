import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import Classes.Accounts.SavingsAccount;
import Classes.Clients.Client;
import Classes.Clients.IndividualClient;
import Classes.Clients.VIPClient;

public class SavingsAccountTest{
    
    private void setLastInterestDate(SavingsAccount acc, LocalDate date) throws Exception{
        Field f = acc.getClass().getSuperclass().getDeclaredField("lastInterestDate");
        f.setAccessible(true);
        f.set(acc, date);
    }
 
    @Test
    //Tests that regular clients receive 2% interest.
    void regularClientReceivesTwoPercentInterest(){
        Client c = new IndividualClient("Amy", "password123");
        SavingsAccount acc = new SavingsAccount(c);
        acc.deposit(1000.0);
 
        acc.applyInterest();
 
        assertEquals(1020.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that VIP clients receive 3% interest.
    void vipClientReceivesThreePercentInterest(){
        Client c = new VIPClient("Ben", "password123");
        SavingsAccount acc = new SavingsAccount(c);
        acc.deposit(1000.0);
 
        acc.applyInterest();
 
        assertEquals(1030.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that interest is not applied more than once in the same month.
    void interestNotAppliedTwiceInSameMonth(){
        Client c = new IndividualClient("Cara", "password123");
        SavingsAccount acc = new SavingsAccount(c);
        acc.deposit(1000.0);
 
        acc.applyInterest();
        acc.applyInterest();
 
        assertEquals(1020.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that interest can be applied again after a month has passed.
    void interestAppliedAgainAfterOneMonth() throws Exception{
        Client c = new IndividualClient("Dan", "password123");
        SavingsAccount acc = new SavingsAccount(c);
        acc.deposit(1000.0);
 
        setLastInterestDate(acc, LocalDate.now().minusMonths(2));
        acc.applyInterest();
 
        assertEquals(1020.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that isInterestDue returns true if interest has never been applied.
    void isInterestDueTrueWhenNeverApplied(){
        Client c = new IndividualClient("Eli", "password123");
        SavingsAccount acc = new SavingsAccount(c);
 
        assertTrue(acc.isInterestDue());
    }
 
    @Test
    //Tests that isInterestDue returns false after interest has been applied.
    void isInterestDueFalseAfterApplied(){
        Client c = new IndividualClient("Fay", "password123");
        SavingsAccount acc = new SavingsAccount(c);
        acc.deposit(100.0);
 
        acc.applyInterest();
 
        assertFalse(acc.isInterestDue());
    }
}
