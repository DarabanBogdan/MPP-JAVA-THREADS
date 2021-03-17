package Main;

import GUI.Login;
import Repo.RepoAngajat;
import Repo.RepoExcursie;
import Repo.RepoRezervare;
import Service.ServiceClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.Socket;

public class MainClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, 5056);




        RepoAngajat repoAngajat=new RepoAngajat();
        RepoExcursie repoExcursie=new RepoExcursie();
        RepoRezervare repoRezervare=new RepoRezervare();
        ServiceClient service=new ServiceClient(repoAngajat,repoRezervare,repoExcursie,s);
        Parent root;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MainClient.class.getResource("/Login.fxml"));
        Login afis=new Login(service,primaryStage);

        loader.setController(afis);
        root = loader.load();
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root ));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}