package GUI;

import Domain.Excursie;
import Domain.Rezervare;
import Service.ServiceClient;
import Main.MainClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Vanzare {

    private ServiceClient service;
    private Stage stage;



    @FXML private Button LogoutButton;

    @FXML private TableColumn<Rezervare, String> NumeClientColumn;
    @FXML private TableColumn<Rezervare, String> TelefonColumn;
    @FXML private TableColumn<Rezervare, String> BileteColumn;

    @FXML private TableColumn<Excursie, String> ObiectivColumn;
    @FXML private TableColumn<Excursie, String> ObiectivColumnSearch;
    @FXML private TableColumn<Excursie, String> FirmaColumn;
    @FXML private TableColumn<Excursie, String> FirmaColumnSearch;
    @FXML private TableColumn<Excursie, String> OraPlecareColumn;
    @FXML private TableColumn<Excursie, String> OraPlecareColumnSearch;
    @FXML private TableColumn<Excursie, String> PretColumn;
    @FXML private TableColumn<Excursie, String> PretColumnSearch;
    @FXML private TableColumn<Excursie, String> LocuriDisponibileColumn;
    @FXML private TableColumn<Excursie, String> LocuriDisponibileColumnSearch;


    @FXML private TextField ObiectivTuristicField;
    @FXML private TextField NumeClientField;
    @FXML private TextField TelefonField;
    @FXML private TextField NumarBileteField;
    @FXML private TextField OraInceputField;
    @FXML private TextField MinutInceputField;
    @FXML private TextField OraFinalField;
    @FXML private TextField MinutFinalField;

    @FXML private TableView<Excursie> TableDefault;
    @FXML private TableView<Excursie> TableDefaultSearch;
    @FXML private TableView<Rezervare> TableRezervare;



    public void initializare(ServiceClient service, Stage stage) throws IOException, ClassNotFoundException {
        this.service=service;
        this.stage=stage;
        ShowTable();
    }

    public void Complete(){

        ObiectivTuristicField.setText(TableDefault.getSelectionModel().getSelectedItem().getNumeObiectivTuristic());

    }
    public void Cauta() throws IOException, ClassNotFoundException {

        TableRezervare.getItems().clear();
        Iterable<Excursie> m=service.findAllMatch(ObiectivTuristicField.getText(),OraInceputField.getText()+":"+
                MinutInceputField.getText(),OraFinalField.getText()+":"+MinutFinalField.getText());
        ObservableList<Excursie> l= FXCollections.observableArrayList();

        for(Excursie mec:m){
            l.add(mec);
        }

        this.ObiectivColumnSearch.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeObiectivTuristic()));
        this.FirmaColumnSearch.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeFirma()));

        this.OraPlecareColumnSearch.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOraPlecare()));
        this.PretColumnSearch.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPret().toString()));
        this.LocuriDisponibileColumnSearch.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumarLocuriDisponibile().toString()));

        TableDefaultSearch.setItems(l);


    }
    public void Rezerva() throws IOException, ClassNotFoundException {


        this.service.addRezervare(new Rezervare(TableDefaultSearch.getSelectionModel().getSelectedItem().getId(),NumeClientField.getText(),TelefonField.getText(),Integer.parseInt(NumarBileteField.getText())),TableDefaultSearch.getSelectionModel().getSelectedItem().getId());
        this.ShowTable();
        this.Cauta();

    }



    public void ShowTable() throws IOException, ClassNotFoundException {
        Iterable<Excursie> m=service.findAllExcursie();
        ObservableList<Excursie> l= FXCollections.observableArrayList();

        for(Excursie mec:m){
            l.add(mec);
        }

        this.ObiectivColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeObiectivTuristic()));
        this.FirmaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeFirma()));

        this.OraPlecareColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOraPlecare()));
        this.PretColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPret().toString()));
        this.LocuriDisponibileColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumarLocuriDisponibile().toString()));

        TableDefault.setItems(l);


        this.LocuriDisponibileColumn.setCellFactory(column -> {
            return new TableCell<Excursie, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty

                        setText(item); //Put the String data in the cell

                        //We get here all the info of the Person of this row
                        Excursie auxPerson = getTableView().getItems().get(getIndex());

                        // Style all persons wich name is "Edgard"
                        if (auxPerson.getNumarLocuriDisponibile()==0) {
                            setTextFill(Color.BLACK); //The text in red
                            setStyle("-fx-background-color: RED"); //The background of the cell in yellow
                        } else {
                            //Here I see if the row of this cell is selected or not
                            if(getTableView().getSelectionModel().getSelectedItems().contains(auxPerson))
                                setTextFill(Color.WHITE);
                            else
                                setTextFill(Color.BLACK);
                        }
                    }
                }
            };
        });


    }

    public void ShowTableRezervare() throws IOException, ClassNotFoundException {
        Iterable<Rezervare> m=service.findAllRezervare(TableDefaultSearch.getSelectionModel().getSelectedItem());
        ObservableList<Rezervare> l= FXCollections.observableArrayList();

        for(Rezervare mec:m){
            l.add(mec);
        }

        this.NumeClientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeClient()));
        this.TelefonColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumarTelefon()));
        this.BileteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumarBilete().toString()));

        TableRezervare.setItems(l);

    }


    public void Logout() throws IOException {


        Parent root;
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MainClient.class.getResource("/Login.fxml"));
        Login afis=new Login(service,stage);

        loader.setController(afis);
        root = loader.load();
        stage.setTitle("Login");
        stage.setScene(new Scene(root ));
    }
}
