package com.example.etlap;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainController {
    @FXML
    public ListView kategoriaList;
    @FXML
    public TextField txtLeiras;
    @FXML
    public TableView etlapTable;
    @FXML
    public TableColumn colNev;
    @FXML
    public TableColumn colKategoria;
    @FXML
    public TableColumn colAr;
    @FXML
    public Spinner ftEmelesInput;
    @FXML
    public Spinner szazalekEmelesInput;
    private EtlapDB db;

    public void initialize(){
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        try {
            db = new EtlapDB();
            etelListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
    public void onEtelFelveteleButtonClick(ActionEvent actionEvent) {
    }

    public void onEtlapTorlesButtonClick(ActionEvent actionEvent) {
    }

    public void onSzazalekEmelesButtonClick(ActionEvent actionEvent) {
    }

    public void onFtEmelesButtonClick(ActionEvent actionEvent) {
    }

    public void onKategoriaHozzaadButtonClick(ActionEvent actionEvent) {
    }

    public void onKategoriaTorlesButtonClick(ActionEvent actionEvent) {
    }
    private void etelListaFeltolt(){
        try {
            List<Etel> etelList = db.getEtelek();
            etlapTable.getItems().clear();
            for(Etel etel: etelList){
                etlapTable.getItems().add(etel);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
    protected void hibaKiir(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(e.getClass().toString());
        alert.setContentText(e.getMessage());
        Timer alertTimer = new Timer();
        alertTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> alert.show());
            }
        }, 500);
    }
}
