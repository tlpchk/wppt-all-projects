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
import main.Product;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class productViewController extends Controller{
    @FXML private TableView<Product> productTable;

    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> poducentNameColumn;
    @FXML private TableColumn<Product,Integer> countColumn;
    @FXML private TableColumn<Product,Float> priceColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location,resources);

        ObservableList<Product> products = FXCollections.observableArrayList();
        Connection conn = db.connect();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select * " +
                            "from products ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getFloat(4));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        poducentNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("producentName"));
        countColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("count"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
        productTable.setItems(products);

    }
}