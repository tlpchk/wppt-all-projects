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

public class addKeysViewController extends Controller {

    @FXML TextField product;
    @FXML TextField count;

    @FXML Label error;

    @FXML
    public void addK(ActionEvent event) throws Exception {
        if(product.getText().equals("") || count.getText().equals("")){
            error.setTextFill(Paint.valueOf("e41b1b"));
            error.setText("Product and count cannot be empty");
            return;
        }

        Connection conn = db.connect();
        try {
            CallableStatement callableStatement = conn.prepareCall("{call addKeys(?,?)}");

            callableStatement.setString(1,product.getText());
            callableStatement.setString(2,count.getText());
            try {
                callableStatement.executeUpdate();
            }catch (SQLException e){
                error.setTextFill(Paint.valueOf("e41b1b"));
                error.setText(e.getMessage());
                return;
            }
            error.setTextFill(Paint.valueOf("#1de31b"));
            error.setText("Keys added");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}