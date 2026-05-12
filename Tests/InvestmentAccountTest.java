import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import Classes.Accounts.ChequeingAccount;
import Classes.Accounts.InvestmentAccount;
import Classes.Clients.Client;
import Classes.Clients.IndividualClient;
import Classes.Clients.VIPClient;

public class InvestmentAccountTest{
    
    private void setField(InvestmentAccount acc, String fieldName, LocalDate date) throws Exception {
        Field f = acc.getClass().getSuperclass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(acc, date);
    }
 
    @Test
    //Tests that direct withdrawals from the investment account are not allowed.
    void directWithdrawAlwaysThrows(){
        Client c = new IndividualClient("Quinn", "password123");
        InvestmentAccount acc = new InvestmentAccount(c);
        acc.deposit(1000.0);
 
        assertThrows(UnsupportedOperationException.class, () -> acc.withdraw(100.0));
    }
 
    @Test
    //Tests that transfers to the chequeing account are blocked during the lock period.
    void transferSucceedsAfterLockPeriod() throws Exception{
        Client c = new IndividualClient("Rachel", "password123");
        InvestmentAccount inv = new InvestmentAccount(c);
        ChequeingAccount chq = new ChequeingAccount(c);
        inv.deposit(1000.0);
 
        setField(inv, "openingDate", LocalDate.now().minusDays(400));
        inv.transfertoChequeing(chq, 300.0);
 
        assertEquals(700.0, inv.getBalance(), 0.001);
        assertEquals(300.0, chq.getBalance(), 0.001);
    }
 
    @Test
    //Tests that transfers to the chequeing account are blocked during the lock period.
    void transferBlockedDuringLockPeriod(){
        Client c = new IndividualClient("Sam", "password123");
        InvestmentAccount inv = new InvestmentAccount(c);
        ChequeingAccount chq = new ChequeingAccount(c);
        inv.deposit(1000.0);
 
        inv.transfertoChequeing(chq, 300.0);
 
        assertEquals(1000.0, inv.getBalance(), 0.001);
        assertEquals(0.0, chq.getBalance(), 0.001);
    }
 
    @Test
    //Tests that transfers to the chequeing account are blocked during the lock period.
    void transferExceedingBalanceDoesNotProceed() throws Exception{
        Client c = new IndividualClient("Tara", "password123");
        InvestmentAccount inv = new InvestmentAccount(c);
        ChequeingAccount chq = new ChequeingAccount(c);
        inv.deposit(100.0);
 
        setField(inv, "openingDate", LocalDate.now().minusDays(400));
        inv.transfertoChequeing(chq, 500.0);
 
        assertEquals(100.0, inv.getBalance(), 0.001);
        assertEquals(0.0, chq.getBalance(), 0.001);
    }
 
    @Test
    //Tests that regular clients receive 5% interest and VIP clients receive 6% interest when applied.
    void regularClientReceivesFivePercentInterest(){
        Client c = new IndividualClient("Uma", "password123");
        InvestmentAccount acc = new InvestmentAccount(c);
        acc.deposit(1000.0);
 
        acc.applyInterest();
 
        assertEquals(1050.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that regular clients receive 5% interest and VIP clients receive 6% interest when applied.
    void vipClientReceivesSixPercentInterest(){
        Client c = new VIPClient("Vera", "password123");
        InvestmentAccount acc = new InvestmentAccount(c);
        acc.deposit(1000.0);
 
        acc.applyInterest();
 
        assertEquals(1060.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that interest cannot be applied more than once in the same month.
    void interestNotAppliedTwiceInSameMonth(){
        Client c = new IndividualClient("Will", "password123");
        InvestmentAccount acc = new InvestmentAccount(c);
        acc.deposit(1000.0);
 
        acc.applyInterest();
        acc.applyInterest();
 
        assertEquals(1050.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that interest can be applied again after a month has passed since the last application.
    void interestAppliedAgainAfterOneMonth() throws Exception{
        Client c = new IndividualClient("Xena", "password123");
        InvestmentAccount acc = new InvestmentAccount(c);
        acc.deposit(1000.0);
 
        setField(acc, "lastInterestDate", LocalDate.now().minusMonths(2));
        acc.applyInterest();
 
        assertEquals(1050.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that the isInterestDue method returns true when interest has never been applied and false after interest has been applied.
    void isInterestDueTrueWhenNeverApplied(){
        Client c = new IndividualClient("Yara", "password123");
        InvestmentAccount acc = new InvestmentAccount(c);
 
        assertTrue(acc.isInterestDue());
    }
 
    @Test
    //Tests that the isInterestDue method returns true when interest has never been applied and false after interest has been applied.
    void isInterestDueFalseAfterApplied(){
        Client c = new IndividualClient("Zach", "password123");
        InvestmentAccount acc = new InvestmentAccount(c);
        acc.deposit(100.0);
 
        acc.applyInterest();
 
        assertFalse(acc.isInterestDue());
    }
}
