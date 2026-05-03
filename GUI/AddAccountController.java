package GUI;

import Classes.Accounts.ChequeingAccount;
import Classes.Accounts.SavingsAccount;
import Classes.Accounts.InvestmentAccount;
import Classes.Clients.Client;
import Exceptions.MissingChequeingAccountException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddAccountController{

    private Client currentClient;
    private Runnable onSuccess;

    @FXML 
    private ComboBox<String> accountTypeCombo;
    @FXML 
    private Label errorLabel;
    @FXML 
    private Button confirmButton;

    @FXML
    public void initialize(){
        accountTypeCombo.getItems().addAll("Chequeing", "Savings", "Investment");
        accountTypeCombo.getSelectionModel().selectFirst();
        errorLabel.setVisible(false);
    }

    public void setClient(Client client){
        this.currentClient = client;
    }

    public void setOnSuccess(Runnable callback){
        this.onSuccess = callback;
    }

    @FXML
    private void onConfirm(){
        String selected = accountTypeCombo.getValue();
        errorLabel.setVisible(false);

        try{
            switch(selected){
                case "Chequeing":
                    currentClient.addAccount(new ChequeingAccount(currentClient));
                    break;
                case "Savings":
                    currentClient.addAccount(new SavingsAccount(currentClient));
                    break;
                case "Investment":
                    currentClient.addAccount(new InvestmentAccount(currentClient));
                    break;
            }

            if(onSuccess != null){
                onSuccess.run();
            }

            closeWindow();

        } 
        catch(MissingChequeingAccountException e){
            errorLabel.setText("You must have a Chequeing account first.");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void onCancel(){
        closeWindow();
    }

    private void closeWindow(){
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}