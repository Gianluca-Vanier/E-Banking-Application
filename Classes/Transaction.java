package Classes;

import java.time.LocalDate;

public class Transaction
{
    public String transactionID;
    public String type;
    public double amount;
    public LocalDate date;
    public static int counter = 0;

    public Transaction(String type, double amount){
        this.transactionID = ++counter + "";
        this.date = LocalDate.now();
        this.type = type;
        this.amount = amount;
    }
}
