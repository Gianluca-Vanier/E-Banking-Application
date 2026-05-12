import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Classes.Accounts.ChequeingAccount;
import Classes.Clients.Client;
import Classes.Clients.IndividualClient;

public class AccountTest{
    
    //Helper method to create an individual client for testing.
    private Client individualClient(String name){
        return new IndividualClient(name, "password123");
    }
 
    @Test
    //Tests that depositing a positive amount increases the balance.
    void depositPositiveAmountIncreasesBalance(){
        Client c = individualClient("Alice");
        ChequeingAccount acc = new ChequeingAccount(c);
 
        acc.deposit(200.0);
 
        assertEquals(200.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that depositing multiple times accumulates the balance.
    void depositMultipleTimesAccumulates(){
        Client c = individualClient("Bob");
        ChequeingAccount acc = new ChequeingAccount(c);
 
        acc.deposit(100.0);
        acc.deposit(50.0);
        acc.deposit(25.0);
 
        assertEquals(175.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that depositing adds a transaction entry.
    void depositAddsTransactionEntry(){
        Client c = individualClient("Carol");
        ChequeingAccount acc = new ChequeingAccount(c);
 
        acc.deposit(300.0);
 
        assertEquals(1, acc.transactions.size());
        assertEquals("deposit", acc.transactions.get(0).type);
        assertEquals(300.0, acc.transactions.get(0).amount, 0.001);
    }
 
    @Test
    //Tests that depositing zero or a negative amount is ignored.
    void depositZeroOrNegativeIsIgnored(){
        Client c = individualClient("Dave");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(100.0);
 
        acc.deposit(0);
        acc.deposit(-50.0);
 
        assertEquals(100.0, acc.getBalance(), 0.001);
        assertEquals(1, acc.transactions.size());
    }
 
    @Test
    //Tests that the initial balance after account creation is zero.
    void initialBalanceIsZero(){
        Client c = individualClient("Eve");
        ChequeingAccount acc = new ChequeingAccount(c);
 
        assertEquals(0.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that withdrawing a valid amount decreases the balance.
    void withdrawValidAmountDecreasesBalance(){
        Client c = individualClient("Frank");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(500.0);
 
        acc.withdraw(200.0);
 
        assertEquals(300.0, acc.getBalance(), 0.001);
    }
 
    @Test
    //Tests that withdrawing adds a transaction entry.
    void withdrawAddsTransactionEntry(){
        Client c = individualClient("Grace");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(500.0);
 
        acc.withdraw(100.0);
 
        long withdrawCount = acc.transactions.stream().filter(t -> "withdraw".equals(t.type)).count();
        assertEquals(1, withdrawCount);
    }
 
    @Test
    //Tests that withdrawing an amount exceeding the balance results in a negative balance.
    void withdrawExceedingBalanceGoesNegative(){
        Client c = individualClient("Hank");
        ChequeingAccount acc = new ChequeingAccount(c);
        acc.deposit(50.0);
 
        acc.withdraw(200.0);
 
        assertTrue(acc.getBalance() < 0);
    }
}
