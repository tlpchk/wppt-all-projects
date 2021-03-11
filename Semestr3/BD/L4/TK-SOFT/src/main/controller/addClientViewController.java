package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.sql.*;

public class addClientViewController extends Controller {
    @FXML private TextField name;
    @FXML private TextField surname;
    @FXML private TextField mail;
    @FXML private TextField number;

    @FXML private Label error;

    @FXML public void addClient(ActionEvent event) throws Exception {
        if(mail.getText().equals("")){
            error.setTextFill(Paint.valueOf("e41b1b"));
            error.setText("Email cannot be empty");
            return;
        }

        Connection conn = db.connect();
        try {
            CallableStatement callableStatement = conn.prepareCall("{call addClient(?,?,?,?)}");

            int num=0;
            try{
                if(!number.getText().equals("")) {
                    num = Integer.parseInt(number.getText());
                }
            }catch (NumberFormatException e){
                error.setTextFill(Paint.valueOf("e41b1b"));
                error.setText("Bad number format");
                return;
            }

            if(num==0){
                callableStatement = conn.prepareCall("{call addClient(?,?,?,null)}");
            }else{
                callableStatement.setInt(4, num);
            }
            callableStatement.setString(1,name.getText());
            callableStatement.setString(2,surname.getText());
            callableStatement.setString(3,mail.getText());
            try {
                callableStatement.executeUpdate();
            }catch (SQLException e){
                error.setTextFill(Paint.valueOf("e41b1b"));
                error.setText(e.getMessage());
                return;
            }
            error.setTextFill(Paint.valueOf("#1de31b"));
            error.setText("client added");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}