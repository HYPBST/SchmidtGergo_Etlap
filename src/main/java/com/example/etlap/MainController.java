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
    public TableView<Kategoria>  kategoriaList;
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
    public TableColumn colKategoriaNev;
    private EtlapDB db;


    public void initialize(){
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        colKategoriaNev.setCellValueFactory(new PropertyValueFactory<>("nev"));

        try {
            db = new EtlapDB();
            kategoriaFeltolt();
            etelListaFeltolt();
            for (Kategoria k:db.getKategoriak()){
                System.out.println(k.getNev());
            }
        } catch (SQLException e) {
            hibaKiir(e);
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
                etel.setArSzazalek((int) szazalekEmelesInput.getValue());
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
        try {
            Controller hozzadas = ujAblak("kategoria-hozzaad-view.fxml", "Kategória hozzáadása",
                    320, 400);
            hozzadas.getStage().setOnCloseRequest(event -> kategoriaFeltolt());
            hozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }
    @FXML
    public void onKategoriaTorlesButtonClick(ActionEvent actionEvent) {
        if(kategoriaList.getSelectionModel().getSelectedIndex()==-1){
            alert("Válasszon ki egy elemet a törléshez");
        }else {
            try {
                db=new EtlapDB();
                db.kategoriaTorles(kategoriaList.getSelectionModel().getSelectedItem().getId());
                alert("A kategória törlése sikeres volt!");
                kategoriaFeltolt();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
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
    public void kategoriaFeltolt(){

        try {
            db=new EtlapDB();
            kategoriaList.getItems().clear();
            for (Kategoria k :db.getKategoriak()){
                if(!kategoriaList.getItems().contains(k)){
                    kategoriaList.getItems().add(k);
                    colKategoriaNev.setCellValueFactory(k.getNev());
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
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
