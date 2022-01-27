package com.example.etlap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class EtlapHozzaadController extends Controller {
    @FXML
    public TextField nevInput;
    @FXML
    public TextArea leirasInput;
    @FXML
    public ComboBox kategoriaInput;
    @FXML
    public Spinner arInput;
    private EtlapDB db;

    public void initialize() {
        kategoriaFeltolt();
    }
    @FXML
    public void onHozzaadButtonClick(ActionEvent actionEvent) {
        String nev=nevInput.getText();
        String leiras=leirasInput.getText();
        String kategoria=kategoriaInput.getSelectionModel().getSelectedItem().toString();
        int ar=(Integer) arInput.getValue();
        if (nev.isEmpty()){
            alert("Név megadása kötelező");
            return;
        }
        if (kategoria.isEmpty()){
            alert("Kategória megadása kötelező");
            return;
        }
        if (leiras.isEmpty()){
            alert("Leírás megadása kötelező");
            return;
        }
        int siker = 0;
        try {
            siker = db.etelHozzaadasa(nev, leiras, ar, kategoria);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (siker == 1){
            alert("Étel hozzáadása sikeres");
        } else {
            alert("Étel hozzáadása sikeretelen");
        }
    }
    @FXML
    public void kategoriaFeltolt(){
        try {
            db=new EtlapDB();
            for (Kategoria k:db.getKategoriak()){
                kategoriaInput.getItems().add(k.getNev());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
