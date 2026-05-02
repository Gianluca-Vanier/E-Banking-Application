package Classes;

import java.util.ArrayList;
import Classes.Clients.Client;

public class Main
{
    public static ArrayList<Client> clients = new ArrayList<>();
    public static void main(String[] args){
        clients = DataManager.loadClients();
        DataManager.linkAccountsToOwners(clients);

        // START JAVA FX

        DataManager.saveClients(clients);
    }
}