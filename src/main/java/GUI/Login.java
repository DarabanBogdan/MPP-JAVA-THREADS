package GUI;

import Domain.Angajat;
import Service.ServiceClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {

    @FXML private javafx.scene.control.TextField Username;
    @FXML private javafx.scene.control.PasswordField Password;
    @FXML private javafx.scene.control.Button LoginButton;

    private ServiceClient service;
    private Stage stage;

    public Login(ServiceClient service, Stage stage){this.service=service;this.stage=stage;}

    public void Autentificare() throws IOException, ClassNotFoundException {

        if(service.login(new Angajat(Username.getText(),Password.getText()))){
           // Stage stage=new Stage();
            stage.setTitle("Vanzare bilete");
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(Login.class.getResource("/Vanzare.fxml"));

            Parent root=loader.load();
            Vanzare vanzare=loader.getController();
            vanzare.initializare(this.service,stage);
            stage.setScene(new Scene(root));
            stage.show();


        }
        else
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Username or password incorect!");
            Username.clear();
            Password.clear();
            alert.show();

        }


    }
}
