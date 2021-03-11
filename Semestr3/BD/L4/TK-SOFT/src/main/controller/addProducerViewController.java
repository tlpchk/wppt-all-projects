package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import main.DbConnector;
import main.Main;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class addProducerViewController extends Controller {

    @FXML private TextField name;
    @FXML private TextField country;
    @FXML private TextField contact;

    @FXML private Label error;

    @FXML
    public void addProd(ActionEvent event) throws Exception {
        if(name.getText().equals("")){
            error.setTextFill(Paint.valueOf("e41b1b"));
            error.setText("Name cannot be empty");
            return;
        }

        Connection conn = db.connect();
        try {
            CallableStatement callableStatement = conn.prepareCall("{call addProd(?,?,?)}");

            callableStatement.setString(1,name.getText());
            callableStatement.setString(2,country.getText());
            callableStatement.setString(3,contact.getText());
            try {
                callableStatement.executeUpdate();
            }catch (SQLException e){
                error.setTextFill(Paint.valueOf("e41b1b"));
                error.setText(e.getMessage());
                return;
            }
            error.setTextFill(Paint.valueOf("#1de31b"));
            error.setText("Producer added");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}