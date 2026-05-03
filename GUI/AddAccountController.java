package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import Classes.Accounts.*;
import Classes.Clients.Client;

public class AddAccountController 
{
    @FXML 
    private ComboBox<String> accountTypeCB;
    @FXML 
    private Button confirm;
    @FXML 
    private Button cancel;
        
    private Client client;
    
    public void setClient(Client client){
        this.client = client;
    }

    @FXML
    public void initialize(){
        accountTypeCB.getItems().addAll("Chequeing", "Savings", "Investment");
        accountTypeCB.setValue("Chequeing");
    }

    @FXML
    protected void onConfirm(ActionEvent event){
        String type = accountTypeCB.getValue();

        Account acc = null;

        switch(type){
            case "Chequeing":  
                 acc = new ChequeingAccount(client); 
                break;
             case "Savings":   
                acc = new SavingsAccount(client); 
                break;
            case "Investment": 
                acc = new InvestmentAccount(client); 
                break;
        }

        client.addAccount(acc);
        closeWindow(event);
    }

    @FXML
    protected void onCancel(ActionEvent event){
        closeWindow(event);
    }

    private void closeWindow(ActionEvent event){
        Stage stage = (Stage) accountTypeCB.getScene().getWindow();
        stage.close();
    }
}