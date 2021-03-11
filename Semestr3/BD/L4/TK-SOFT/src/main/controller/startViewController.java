package main.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.DbConnector;
import main.Main;//kadhsj

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class startViewController extends Controller{
    private final String logErrorMsg = "";

    @FXML private CheckBox isAdmin;

    @FXML private Label errorLabel;

    @FXML private TextField login;

    @FXML private PasswordField password;

    @FXML
    public void logIn(ActionEvent event) throws Exception {
        errorLabel.setText("");

        Connection conn = db.connect();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                                                "select * " +
                                                     "from `tk-soft`.passwords " +
                                                     "where Login=? and Password=?");
            preparedStatement.setString(1,login.getText());
            preparedStatement.setString(2,password.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                if (isAdmin.isSelected()) {
                    main.goToView("view/homeAdminView.fxml");
                } else {

                    main.goToView("view/homeWorkerView.fxml");
                }
            }
            else{
                errorLabel.setText("Bad login or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
