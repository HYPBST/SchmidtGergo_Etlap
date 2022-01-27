package com.example.etlap;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class KategoriaHozzaadController extends Controller{
    public TextField kategoriaNevInput;
    private EtlapDB db;

    public void onHozzaadButtonClick(ActionEvent actionEvent) {
        if(kategoriaNevInput.getText().isEmpty()){
            alert("Kategória megadása kötelező");
        }else{
            try {
                db=new EtlapDB();
                if (!db.getKategoriak().contains(kategoriaNevInput.getText())) {
                    db.kategoriaHozzaadas(kategoriaNevInput.getText().toLowerCase());
                    alert("A kategória hozzáadása sikeres volt");
                }else {
                    alert("A kategóriák már tartalmazza ezt a kategóriát.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
