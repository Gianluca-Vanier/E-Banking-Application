package GUI;

import java.util.ArrayList;
import Classes.DataManager;
import Classes.Clients.Client;
import javafx.application.Application;

public class Main
{
    public static ArrayList<Client> clients = new ArrayList<>();
    
    public static void main(String[] args){
        clients = DataManager.loadClients();
        DataManager.linkAccountsToOwners(clients);

        Application.launch(AppFX.class, args);

        DataManager.saveClients(clients);
    }
}