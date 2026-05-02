package GUI;

import java.util.ArrayList;
import Classes.DataManager;
import Classes.Clients.Client;
import javafx.application.Application;

public class Main
{
    public static ArrayList<Client> clients = new ArrayList<>();
    public static void main(String[] args){
        Application.launch(AppFX.class, args);
        
        /*clients = DataManager.loadClients();
        DataManager.linkAccountsToOwners(clients);

        // START JAVA FX

        DataManager.saveClients(clients);*/
    }
}