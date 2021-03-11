package main.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Client;
import main.DbConnector;
import main.Main;//kadhsj
import main.Order;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class orderViewController extends Controller{
    @FXML private TableView<Order> orderTable;

    @FXML private TableColumn<Order,Integer> idColumn;
    @FXML private TableColumn<Order,String> productColumn;
    @FXML private TableColumn<Order,String> statusColumn;
    @FXML private TableColumn<Order,String> clientColumn;
    @FXML private TableColumn<Order,Integer> countColumn;
    @FXML private TableColumn<Order,String> detailsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location,resources);

        Connection conn = db.connect();

        ObservableList<Order> orders = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select * " + "from orders ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                orders.add(new Order(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        productColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("product"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("client"));
        countColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("count"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<Order, String >("details"));

        orderTable.setItems(orders);
    }
}
