import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import Classes.Accounts.ChequeingAccount;
import Classes.Accounts.InvestmentAccount;
import Classes.Accounts.SavingsAccount;
import Classes.Clients.Client;
import Classes.Clients.IndividualClient;
import Exceptions.MissingChequeingAccountException;

public class ClientTest{

    //Helper method to create an individual client with a given name.
    private Client individualClient(String name){
        return new IndividualClient(name, "password123");
    }
 
    @Test
    //Tests that a new client starts with no accounts.
    void addChequeingAccountSucceeds(){
        Client c = individualClient("Gus");
 
        assertDoesNotThrow(() -> c.addAccount(new ChequeingAccount(c)));
        assertEquals(1, c.accounts.size());
    }
 
    @Test
    //Tests that adding a savings account without a chequeing account throws the expected exception.
    void addSavingsWithoutChequeingThrows(){
        Client c = individualClient("Hal");
 
        assertThrows(MissingChequeingAccountException.class,
                () -> c.addAccount(new SavingsAccount(c)));
    }
 
    @Test
    //Tests that adding an investment account without a chequeing account throws the expected exception.
    void addInvestmentWithoutChequeingThrows(){
        Client c = individualClient("Ivy");
 
        assertThrows(MissingChequeingAccountException.class,
                () -> c.addAccount(new InvestmentAccount(c)));
    }
 
    @Test
    //Tests that adding a savings account after a chequeing account succeeds.
    void addSavingsAfterChequeingSucceeds() throws Exception{
        Client c = individualClient("Jon");
        c.addAccount(new ChequeingAccount(c));
 
        assertDoesNotThrow(() -> c.addAccount(new SavingsAccount(c)));
        assertEquals(2, c.accounts.size());
    }
 
    @Test
    //Tests that adding an investment account after a chequeing account succeeds.
    void addInvestmentAfterChequeingSucceeds() throws Exception{
        Client c = individualClient("Kay");
        c.addAccount(new ChequeingAccount(c));
 
        assertDoesNotThrow(() -> c.addAccount(new InvestmentAccount(c)));
    }
  
    @Test
    //Tests that logging in with correct credentials returns true.
    void loginWithCorrectCredentialsReturnsTrue(){
        Client c = individualClient("Leo");
 
        assertTrue(c.login(c.getClientID(), "password123"));
    }
 
    @Test
    //Tests that logging in with wrong password returns false.
    void loginWithWrongPasswordReturnsFalse(){
        Client c = individualClient("Mae");
 
        assertFalse(c.login(c.getClientID(), "wrongpass"));
    }
 
    @Test
    //Tests that logging in with wrong ID returns false.
    void loginWithWrongIdReturnsFalse(){
        Client c = individualClient("Ned");
 
        assertFalse(c.login("9999", "password123"));
    }
  
    @Test
    //Tests that applying interest to all accounts returns the correct count of accounts updated.
    void applyInterestToAllReturnCountOfAccountsUpdated() throws Exception{
        Client c = individualClient("Ora");
        c.addAccount(new ChequeingAccount(c));
 
        SavingsAccount sav = new SavingsAccount(c);
        sav.deposit(1000.0);
        c.addAccount(sav);
 
        InvestmentAccount inv = new InvestmentAccount(c);
        inv.deposit(1000.0);
        c.addAccount(inv);
 
        int count = c.applyInterestToAll();
 
        assertEquals(2, count);
    }
 
    @Test
    //Tests that applying interest to all accounts correctly updates the balances of interest-bearing accounts.
    void applyInterestToAllUpdatesBalances() throws Exception{
        Client c = individualClient("Pete");
        c.addAccount(new ChequeingAccount(c));
 
        SavingsAccount sav = new SavingsAccount(c);
        sav.deposit(1000.0);
        c.addAccount(sav);
 
        c.applyInterestToAll();
 
        assertEquals(1020.0, sav.getBalance(), 0.001);
    }
  
    @Test
    //Tests that applying fees to all accounts returns the correct count of accounts charged.
    void applyFeesToAllChargesChequeingAccount() throws Exception{
        Client c = individualClient("Pat");
        ChequeingAccount chq = new ChequeingAccount(c);
        chq.deposit(100.0);
        c.addAccount(chq);
 
        int count = c.applyFeesToAll();
 
        assertEquals(1, count);
        assertEquals(90.0, chq.getBalance(), 0.001);
    }
 
    @Test
    //Tests that applying fees to all accounts does not double charge if run multiple times in the same month.
    void applyFeesToAllDoesNotDoubleChargeInSameMonth() throws Exception{
        Client c = individualClient("Rex");
        ChequeingAccount chq = new ChequeingAccount(c);
        chq.deposit(100.0);
        c.addAccount(chq);
 
        c.applyFeesToAll();
        int count = c.applyFeesToAll();
 
        assertEquals(0, count);
        assertEquals(90.0, chq.getBalance(), 0.001);
    }
  
    @Test
    //Tests that applying fees to all accounts correctly charges again after a month has passed.
    void maintenanceReturnsTrueWhenNeverRun() throws Exception{
        Client c = individualClient("Sue");
        ChequeingAccount chq = new ChequeingAccount(c);
        chq.deposit(100.0);
        c.addAccount(chq);
 
        assertTrue(c.applyMonthlyMaintenanceIfDue());
    }
 
    @Test
    //Tests that applying fees to all accounts does not charge again if run multiple times in the same month.
    void maintenanceReturnsFalseWhenAlreadyRunThisMonth() throws Exception{
        Client c = individualClient("Tom");
        c.addAccount(new ChequeingAccount(c));
 
        c.applyMonthlyMaintenanceIfDue();
 
        assertFalse(c.applyMonthlyMaintenanceIfDue());
    }
 
    @Test
    //Tests that applying fees to all accounts correctly charges again after a month has passed.
    void maintenanceReturnsTrueAfterOneMonthElapsed() throws Exception{
        Client c = individualClient("Uma");
        c.addAccount(new ChequeingAccount(c));
 
        Field f = c.getClass().getSuperclass().getDeclaredField("lastMaintenanceDate");
        f.setAccessible(true);
        f.set(c, LocalDate.now().minusMonths(2));
 
        assertTrue(c.applyMonthlyMaintenanceIfDue());
    }
  
    @Test
    //Tests that getName returns the correct name.
    void getNameReturnsCorrectName(){
        Client c = individualClient("Vic");
 
        assertEquals("Vic", c.getName());
    }
 
    @Test
    //Tests that getPassword returns the correct password.
    void getPasswordReturnsCorrectPassword(){
        Client c = individualClient("Wes");
 
        assertEquals("password123", c.getPassword());
    }
 
    @Test
    //Testss that getClientID returns a non-null ID.
    void getClientIDReturnsNonNullId(){
        Client c = individualClient("Xia");
 
        assertNotNull(c.getClientID());
    }
}
