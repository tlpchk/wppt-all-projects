package main.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Client;
import main.DbConnector;
import main.Main;//kadhsj

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class findClientViewController extends Controller{
    private String condition="";

    @FXML private TableView<Client> searchedClients;

    @FXML private TableColumn<Client,String> nameColumn;
    @FXML private TableColumn<Client,String> surnameColumn;
    @FXML private TableColumn<Client,String> emailColumn;
    @FXML private TableColumn<Client,Integer> phoneColumn;

    @FXML private TextField nameText;
    @FXML private TextField surnameText;
    @FXML private TextField emailText;
    @FXML private TextField phoneText;

    @FXML public void findOrdersAction(ActionEvent event) throws Exception {
        condition = "";
        ObservableList<Client> searchedClientsArray = FXCollections.observableArrayList();
        Connection conn = db.connect();

        try {

            checkToCondition(nameText,"Name");
            checkToCondition(surnameText,"Surname");
            checkToCondition(emailText,"Mail");
            checkToCondition(phoneText,"Phone_number");

            if(condition.equals("")){
                main.goToView("view/findClientView.fxml");
                return;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select * " +
                            "from clients where "+condition);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Client client = new Client(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
                searchedClientsArray.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        nameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("mail"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("number"));

        searchedClients.setItems(searchedClientsArray);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db=new DbConnector();
    }

    private void checkToCondition(TextField textField,String column){
        if(textField.getText().equals("")){
            return;
        }
        if(!Objects.equals(condition, "")){
            condition+=" and ";
        }
        if(column.equals("Name") || column.equals("Surame") || column.equals("Mail")) {
            condition+=condition += column + " = \'" + textField.getText() + "\'";
        }
        else {
            condition += column + " = " + textField.getText();
        }
    }

}