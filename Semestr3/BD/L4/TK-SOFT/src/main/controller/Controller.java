package main.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.DbConnector;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable{
    protected Main main;
    protected DbConnector db;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = new DbConnector();
    }

    @FXML
    public void logout(ActionEvent event) throws Exception {
        main.goToView("view/startView.fxml");
    }
    public void goWorker(ActionEvent event) throws Exception {
        main.goToView("view/homeWorkerView.fxml");
    }
    public void addEmployee(ActionEvent event) throws Exception {
        main.goToView("view/addEmployeeView.fxml");
    }
    public void addProducer(ActionEvent event) throws Exception {
        main.goToView("view/addProducerView.fxml");
    }
    public void addKeys(ActionEvent event) throws Exception {
        main.goToView("view/addKeysView.fxml");
    }
    public void pass(ActionEvent event) throws Exception {
        main.goToView("view/passView.fxml");
    }
    public void clients(ActionEvent event) throws Exception {
        main.goToView("view/clientView.fxml");
    }
    public void orders(ActionEvent event) throws Exception {
        main.goToView("view/orderView.fxml");
    }
    public void products(ActionEvent event) throws Exception {
        main.goToView("view/productView.fxml");
    }
    public void producers(ActionEvent event) throws Exception {
        main.goToView("view/producerView.fxml");
    }
    public void addClient(ActionEvent event) throws Exception {
        main.goToView("view/addClientView.fxml");
    }
    public void findClient(ActionEvent event) throws Exception {
        main.goToView("view/findClientView.fxml");
    }
    public void addorder(ActionEvent event) throws Exception {
        main.goToView("view/addOrderView.fxml");
    }
    public void findorder(ActionEvent event) throws Exception {
        main.goToView("view/findOrderView.fxml");
    }
    public void findproducer(ActionEvent event) throws Exception {
        main.goToView("view/findProducerView.fxml");
    }
    public void findproduct(ActionEvent event) throws Exception {
        main.goToView("view/findProductView.fxml");
    }
}
