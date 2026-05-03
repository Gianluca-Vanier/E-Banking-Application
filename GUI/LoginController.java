package GUI;

import java.io.IOException;
import Classes.Clients.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;

public class LoginController
{
    @FXML
    private Button SignUpButton;
    @FXML
    private Button LogInButton;
    @FXML
    private TextField ClientIDTF;
    @FXML
    private TextField PasswordTF;


    @FXML
    protected void onLogIn(ActionEvent event){
        String clientID = ClientIDTF.getText();
        String password = PasswordTF.getText();

        for(Client c : Main.clients){
            if(c.getClientID().equals(clientID) && c.getPassword().equals(password)){
                toDashboard(c);
                return;
            }
        }
        
        showAlert("Invalid login");
    }
   
    @FXML
    protected void onSignUpClick(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void toDashboard(Client client){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setClient(client); // 🔥 PASS CLIENT

            Stage stage = (Stage) LogInButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } 
        catch(IOException e){
            System.out.println(e);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
