package GUI;

import java.io.IOException;

import Classes.Clients.Client;
import Classes.Clients.CorporateClient;
import Classes.Clients.IndividualClient;
import Classes.Clients.StudentClient;
import Classes.Clients.VIPClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class SignUpController 
{
    @FXML
    private Button SignUpButton;
    @FXML
    private TextField UsernameTF;
    @FXML
    private TextField PasswordTF;
    @FXML
    private TextField CPasswordTF;
    @FXML
    private Button IndiButton;
    @FXML
    private Button StudentButton;
    @FXML
    private Button CorpButton;
    @FXML
    private Button VIPButton;
    @FXML
    private Label InfoDisplay;

    private String clientType;

    @FXML
    protected void onSignUp(ActionEvent event){
        String username = UsernameTF.getText();
        String password = PasswordTF.getText();
        String cpassword = CPasswordTF.getText();

        if(password.equals(cpassword)){
            Client newClient = null;
            
            switch(clientType){
                case "Individual":
                    newClient = new IndividualClient(username, password);
                    break;
                case "Student":
                    newClient = new StudentClient(username, password);
                    break;
                case "Corporate":
                    newClient = new CorporateClient(username, password);
                    break;
                case "VIP":
                    newClient = new VIPClient(username, password);
                    break;
                default:
                    showAlert("Client tpye not selected");
                    break;
            }

            Main.clients.add(newClient);
            showAlert("Account created. Your ClientID is: " + newClient.getClientID());
            toLogin();
            return;
        }
        
        showAlert("Invalid sign up");
    }

    @FXML
    protected void indiClick(){
        InfoDisplay.setText("Individual Client\n$10 monthly fee per account\n2% interest on Saving accounts\n5% interest on Investment accounts");
        clientType = "Individual";
    }

    @FXML
    protected void studentClick(){
        InfoDisplay.setText("Student Client\\nNo monthly fee\n2% interest on Saving accounts\n5% interest on Investment accounts");
        clientType = "Student";
    }

    @FXML
    protected void corpClick(){
        InfoDisplay.setText("Corporate Client\\n$10 monthly fee per account\n2% interest on Saving accounts\n5% interest on Investment accounts");
        clientType = "Corporate";
    }

    @FXML
    protected void vipClick(){
        InfoDisplay.setText("VIP Client\\nno monthly fee\n2% interest on Saving accounts\n5% interest on Investment accounts\n%1 interest bonus on Savings and Investment accounts");
        clientType = "VIP";
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void toLogin() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage stage = (Stage) SignUpButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } 
        catch(IOException e){
            System.out.println(e);
        }
    }
}
