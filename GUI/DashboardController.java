package GUI;

import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Classes.Clients.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

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

        clientID.setText(currentClient.getClientID());
        username.setText(currentClient.getName());

        //loadClientData();
    }

    @FXML
    protected void onAddAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("openAccount.fxml"));
        Parent root = loader.load();

        AddAccountController controller = loader.getController();
        controller.setClient(currentClient);

        Stage popupStage = new Stage();
        popupStage.setTitle("Open New Account");
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();

        //refreshAccountsDisplay();
    }
}
