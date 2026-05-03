package GUI;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Classes.Clients.Client;
import javafx.fxml.FXML;

public class DashboardController
{
    private Client currentClient;

    @FXML
    private Label clientID;
    @FXML
    private Label username;
    @FXML
    private Label date;
    @FXML
    private Button transaction;
    @FXML
    private Button addAccount;

    public void setClient(Client client) {
        this.currentClient = client;

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date.setText(today.format(formatter));

        //loadClientData();
    }
}
