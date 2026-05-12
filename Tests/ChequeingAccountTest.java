 import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 
import java.lang.reflect.Field;
import java.time.LocalDate;
 
import Classes.Accounts.ChequeingAccount;
import Classes.Clients.Client;
import Classes.Clients.IndividualClient;
import Classes.Clients.StudentClient;
import Classes.Clients.VIPClient;
 
public class ChequeingAccountTest{
 
    private void setLastFeeDate(ChequeingAccount acc, LocalDate date) throws Exception{
        Field f = acc.getClass().getSuperclass().getDeclaredField("lastFeeDate");
        f.setAccessible(true);
        f.set(acc, date);
    }
 
    @Test
    //Tests that individual clients are charged the $10 monthly fee.
    void individualClientIsChargedTenDollarFee(){
        Client c = new IndividualClient("Iris", "password123");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(100.0);
 
        acc.applyMonthlyFee();
 
        assertEquals(90.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that student clients are not charged the monthly fee.
    void studentClientIsNotCharged(){
        Client c = new StudentClient("Jake", "password123");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(100.0);
 
        acc.applyMonthlyFee();
 
        assertEquals(100.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that VIP clients are not charged the monthly fee.
    void vipClientIsNotCharged(){
        Client c = new VIPClient("Kim", "password123");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(100.0);
 
        acc.applyMonthlyFee();
 
        assertEquals(100.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that the monthly fee is not applied more than once in the same month.
    void feeNotAppliedTwiceInSameMonth(){
        Client c = new IndividualClient("Liam", "password123");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(100.0);
 
        acc.applyMonthlyFee();
        acc.applyMonthlyFee();
 
        assertEquals(90.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that the monthly fee is applied again after one month has passed.
    void feeAppliedAgainAfterOneMonth() throws Exception{
        Client c = new IndividualClient("Mia", "password123");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(100.0);
 
        setLastFeeDate(acc, LocalDate.now().minusMonths(2));
        acc.applyMonthlyFee();
 
        assertEquals(90.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that a fee is due when it has never been applied.
    void isFeeDueWhenNeverApplied(){
        Client c = new IndividualClient("Nina", "password123");
        ChequeingAccount acc = new ChequeingAccount(c);
 
        assertTrue(acc.isFeeDue());
    }
 
    @Test
    //Tests that a fee is not due immediately after it has been applied.
    void isFeeDueFalseAfterFeeApplied(){
        Client c = new IndividualClient("Omar", "password123");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(50.0);
 
        acc.applyMonthlyFee();
 
        assertFalse(acc.isFeeDue());
    }
 
    @Test
    //Tests that a fee is due again after one month has passed since the last fee was applied.
    void isFeeDueTrueAfterOneMonth() throws Exception{
        Client c = new IndividualClient("Pam", "password123");
        ChequeingAccount acc = new ChequeingAccount(c);
 
        setLastFeeDate(acc, LocalDate.now().minusMonths(2));
 
        assertTrue(acc.isFeeDue());
    }
}