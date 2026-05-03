package GUI;

import Classes.Clients.Client;

public class DashboardController
{
    private Client currentClient;

    public void setClient(Client client) {
        this.currentClient = client;
        loadClientData();
    }
}
