package com.example.etlap;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class MainController extends Controller{
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
        try {
            for (String k:db.getKategoriak()){
                System.out.println(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onEtelFelveteleButtonClick(ActionEvent actionEvent) {
        try {
            Controller hozzadas = ujAblak("etlap-hozzaad-view.fxml", "Étel hozzáadása",
                    320, 400);
            hozzadas.getStage().setOnCloseRequest(event -> etelListaFeltolt());
            hozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }
    @FXML
    public void onEtlapTorlesButtonClick(ActionEvent actionEvent) {
    }
    @FXML
    public void onSzazalekEmelesButtonClick(ActionEvent actionEvent) throws SQLException {
        if (etlapTable.getSelectionModel().getSelectedIndex()==-1){
            List<Etel> etelList = db.getEtelek();
            for(Etel etel: etelList){
                System.out.println(etel.getAr());
                etel.setArSzazalek((int) szazalekEmelesInput.getValue());
                System.out.println(etel.getAr());
                db.etelModositasa(etel);
            }
            initialize();
        }else{
            Etel kivalasztott= (Etel) etlapTable.getSelectionModel().getSelectedItem();
            kivalasztott.setArSzazalek((Integer) szazalekEmelesInput.getValue());
            db.etelModositasa(kivalasztott);
            initialize();
        }

    }
    @FXML
    public void onFtEmelesButtonClick(ActionEvent actionEvent) throws SQLException {
        if (etlapTable.getSelectionModel().getSelectedIndex()==-1){
            List<Etel> etelList = db.getEtelek();
            for(Etel etel: etelList){
                etel.setAr(etel.getAr()+(Integer) ftEmelesInput.getValue());
                db.etelModositasa(etel);
            }
            initialize();
        }else{
            Etel kivalasztott= (Etel) etlapTable.getSelectionModel().getSelectedItem();
            kivalasztott.setAr(kivalasztott.getAr()+(Integer) ftEmelesInput.getValue());
            db.etelModositasa(kivalasztott);
            initialize();
        }
    }
    @FXML
    public void onKategoriaHozzaadButtonClick(ActionEvent actionEvent) {
    }
    @FXML
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
    @FXML
    public void onItemSelect() {
        if(etlapTable.getSelectionModel().getSelectedIndex()!=-1) {
            Etel kivalasztott = (Etel) etlapTable.getSelectionModel().getSelectedItem();
            String leiras = kivalasztott.getLeiras();
            txtLeiras.setText(leiras);
        }

    }
}
