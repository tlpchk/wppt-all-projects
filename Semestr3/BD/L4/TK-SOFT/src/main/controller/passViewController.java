package main.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Client;
import main.DbConnector;
import main.Main;//kadhsj
import main.Password;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class passViewController extends Controller{
    @FXML private TableView<Password> passTable;

    @FXML private TableColumn<Password,String> LoginColumn;
    @FXML private TableColumn<Password,String> PasswordColumn;
    @FXML private Label errorLabel;
    @FXML private TextField login;
    @FXML private TextField new_pass;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = new DbConnector();
        ObservableList<Password> passwords = FXCollections.observableArrayList();

        Connection conn = db.connect();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select * " +
                            "from passwords ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                passwords.add(new Password(resultSet.getString(1),
                        resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LoginColumn.setCellValueFactory(new PropertyValueFactory<Password, String>("login"));
        PasswordColumn.setCellValueFactory(new PropertyValueFactory<Password, String>("password"));
        passTable.setItems(passwords);
    }
    /*public void passChange(ActionEvent event) throws Exception {
        errorLabel.setText("");

        Connection conn = db.connect();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select * " +
                            "from `tk-soft`.passwords " +
                            "where Login=? and Password=?");
            preparedStatement.setString(1,login.getText());
            preparedStatement.setString(2,new_pass.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(login.getText().equals()){
                errorLabel.setText("Password has changed");
            }
            else{
                errorLabel.setText("Bad login or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
}