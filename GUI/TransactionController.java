package GUI;

import Classes.Accounts.Account;
import Classes.Accounts.ChequeingAccount;
import Classes.Accounts.InvestmentAccount;
import Classes.Clients.Client;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TransactionController {

    private Client currentClient;
    private Runnable onSuccess;

    @FXML private ComboBox<String> accountCombo;
    @FXML private ComboBox<String> targetAccountCombo;
    @FXML private ComboBox<String> operationCombo;
    @FXML private TextField amountField;
    @FXML private Label targetLabel;
    @FXML private Label messageLabel;
    @FXML private Button confirmButton;

    @FXML
    public void initialize(){
        operationCombo.getItems().addAll("Deposit", "Withdraw", "Transfer");
        operationCombo.getSelectionModel().selectFirst();
        messageLabel.setVisible(false);

        operationCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isTransfer = "Transfer".equals(newVal);
            targetAccountCombo.setVisible(isTransfer);
            targetLabel.setVisible(isTransfer);
        });

        targetAccountCombo.setVisible(false);
        targetLabel.setVisible(false);
    }

    public void setClient(Client client){
        this.currentClient = client;
        populateAccountCombos();
    }

    public void setOnSuccess(Runnable callback){
        this.onSuccess = callback;
    }

    private void populateAccountCombos(){
        
        for(Account acc : currentClient.accounts){
            String label = acc.getClass().getSimpleName() + "  —  #" + acc.accountNum;
            accountCombo.getItems().add(label);
            targetAccountCombo.getItems().add(label);
        }

        if(!currentClient.accounts.isEmpty()){
            accountCombo.getSelectionModel().selectFirst();
            targetAccountCombo.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void onConfirm(){
        messageLabel.setVisible(false);
        int sourceIdx = accountCombo.getSelectionModel().getSelectedIndex();
        
        if(sourceIdx < 0){
            showMessage("Please select an account.", true);
            return;
        }

        double amount;

        try{
            amount = Double.parseDouble(amountField.getText().trim());
            
            if (amount <= 0){
                throw new NumberFormatException();
            }
        } 
        catch(NumberFormatException e){
            showMessage("Enter a valid positive amount.", true);
            return;
        }

        Account source = currentClient.accounts.get(sourceIdx);
        String operation = operationCombo.getValue();

        switch(operation){
            case "Deposit":
                source.deposit(amount);
                showMessage(String.format("Deposited $%.2f successfully.", amount), false);
                break;
            case "Withdraw":
                if(source instanceof InvestmentAccount){
                    showMessage("Direct withdrawals from Investment accounts are not allowed. Use Transfer.", true);
                    return;
                }

                if(amount > source.balance){
                    showMessage(String.format("Insufficient funds. Available balance: $%.2f", source.balance), true);
                    return;
                }

                source.withdraw(amount);
                showMessage(String.format("Withdrew $%.2f successfully.", amount), false);
                break;
            case "Transfer":
                int targetIdx = targetAccountCombo.getSelectionModel().getSelectedIndex();
                
                if(targetIdx < 0 || targetIdx == sourceIdx){
                    showMessage("Please select a different target account.", true);
                    return;
                }

                Account target = currentClient.accounts.get(targetIdx);

                if(source instanceof InvestmentAccount){
                    if(!(target instanceof ChequeingAccount)){
                        showMessage("Investment funds can only be transferred to a Chequeing account.", true);
                        return;
                    }
                    
                    long daysOpen = ChronoUnit.DAYS.between(source.openingDate, LocalDate.now());
                    
                    if(daysOpen < 365){
                        long daysLeft = 365 - daysOpen;
                        showMessage(String.format("Investment account is locked. %d day(s) remaining before transfer is allowed.", daysLeft), true);
                        return;
                    }

                    if(amount > source.balance){
                        showMessage(String.format("Insufficient funds. Available balance: $%.2f", source.balance), true);
                        return;
                    }

                    ((InvestmentAccount) source).transfertoChequeing(target, amount);

                } 
                else{
                    if(amount > source.balance){
                        showMessage(String.format("Insufficient funds. Available balance: $%.2f", source.balance), true);
                        return;
                    }

                    source.withdraw(amount);
                    target.deposit(amount);
                }

                showMessage(String.format("Transferred $%.2f successfully.", amount), false);
                break;
        }

        if(onSuccess != null){
            onSuccess.run();
        }

        ((Stage) confirmButton.getScene().getWindow()).close();
    }

    @FXML
    private void onCancel(){
        ((Stage) confirmButton.getScene().getWindow()).close();
    }

    private void showMessage(String msg, boolean isError){
        messageLabel.setText(msg);
        messageLabel.setStyle(isError
            ? "-fx-text-fill: #c0392b; -fx-font-family: 'Segoe UI'; -fx-font-size: 12;"
            : "-fx-text-fill: #27ae60; -fx-font-family: 'Segoe UI'; -fx-font-size: 12;");
        messageLabel.setVisible(true);
    }
}