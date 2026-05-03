package GUI;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Classes.Clients.Client;
import Classes.Accounts.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;

public class DashboardController{

    private Client currentClient;

    @FXML private Label clientID;
    @FXML private Label username;
    @FXML private Label date;
    @FXML private Button transaction;
    @FXML private Button addAccount;
    @FXML private VBox accountsVBox;
    @FXML private VBox balancesVBox;

     public void setClient(Client client){
        this.currentClient = client;

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date.setText(today.format(formatter));

        clientID.setText(currentClient.clientID);
        username.setText(currentClient.getName());

        loadClientData();

        boolean applied = currentClient.applyMonthlyMaintenanceIfDue();

        if(applied){
            loadClientData();
            showAlert(Alert.AlertType.INFORMATION, "Monthly Update", "Interest and monthly fees have been applied to your accounts.");
        }
    }

    public void loadClientData(){
        accountsVBox.getChildren().clear();
        balancesVBox.getChildren().clear();

        Label accountHeader = makeLabel("Account", true);
        Label balanceHeader = makeLabel("Balance", true);
        accountsVBox.getChildren().add(accountHeader);
        balancesVBox.getChildren().add(balanceHeader);

        accountsVBox.getChildren().add(makeDivider());
        balancesVBox.getChildren().add(makeDivider());

        if(currentClient.accounts.isEmpty()){
            accountsVBox.getChildren().add(makeLabel("No accounts yet.", false));
            balancesVBox.getChildren().add(makeLabel("—", false));
            return;
        }

        for(Account acc : currentClient.accounts){
            String accountType = acc.getClass().getSimpleName().replace("Account", " Account");
            String accountLine = accountType + "  #" + acc.accountNum;

            Label nameLabel = makeLabel(accountLine, false);
            Label balanceLabel = makeLabel(String.format("$%.2f", acc.balance), false);

            accountsVBox.getChildren().add(nameLabel);
            balancesVBox.getChildren().add(balanceLabel);
        }
    }

    private Label makeLabel(String text, boolean isHeader){
        Label lbl = new Label(text);
        lbl.setPrefHeight(36);
        lbl.setPadding(new Insets(0, 10, 0, 10));

        if(isHeader){
            lbl.setStyle("-fx-font-family: 'Segoe UI Semibold';" + "-fx-font-size: 13;" + "-fx-text-fill: navy;");
        } 
        else{
            lbl.setStyle("-fx-font-family: 'Segoe UI';" + "-fx-font-size: 13;" + "-fx-text-fill: #333333;");
        }
        return lbl;
    }

    private Label makeDivider(){
        Label div = new Label();
        div.setPrefHeight(1);
        div.setPrefWidth(200);
        div.setStyle("-fx-background-color: #cccccc; -fx-padding: 0;");
        return div;
    }

    @FXML
    private void onAddAccount(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AddAccount.fxml"));
            Parent root = loader.load();

            AddAccountController controller = loader.getController();
            controller.setClient(currentClient);
            controller.setOnSuccess(this::loadClientData);

            Stage stage = new Stage();
            stage.setTitle("Open New Account");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } 
        catch(IOException e){
            System.out.println(e);
        }
    }

    @FXML
    private void onTransaction(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Transaction.fxml"));
            Parent root = loader.load();

            TransactionController controller = loader.getController();
            controller.setClient(currentClient);
            controller.setOnSuccess(this::loadClientData);

            Stage stage = new Stage();
            stage.setTitle("New Transaction");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } 
        catch (IOException e){
            System.out.println(e);
        }
    }

    @FXML
    private void onApplyInterest(){
        if(currentClient.accounts.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "No Accounts", "You have no accounts to apply interest to.");
            return;
        }

        int applied = currentClient.applyInterestToAll();
        loadClientData();

        if(applied == 0){
            showAlert(Alert.AlertType.INFORMATION, "Nothing to Apply", "Interest was already applied this month on all eligible accounts.");
        } 
        else {
            showAlert(Alert.AlertType.INFORMATION, "Interest Applied", "Interest applied to " + applied + " account(s).");
        }
    }

    @FXML
    private void onApplyFees(){
        if(currentClient.accounts.isEmpty()){
            showAlert(Alert.AlertType.WARNING, "No Accounts", "You have no accounts to apply fees to.");
            return;
        }

        int applied = currentClient.applyFeesToAll();
        loadClientData();

        if(applied == 0){
            showAlert(Alert.AlertType.INFORMATION, "Nothing to Apply", "Monthly fees were already applied this month on all eligible accounts.");
        } 
        else{
            showAlert(Alert.AlertType.INFORMATION, "Fees Applied", "Monthly fee applied to " + applied + " account(s).");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}