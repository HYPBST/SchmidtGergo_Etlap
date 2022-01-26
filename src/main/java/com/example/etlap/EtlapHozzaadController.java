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
        sout();
    }
    @FXML
    public void onHozzaadButtonClick(ActionEvent actionEvent) {
    }
    public void sout(){
        try {
            for (String k:db.getKategoriak()){
                System.out.println(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
