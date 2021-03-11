package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    private Stage primaryStage;
    private static BorderPane mainlayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("TK-Soft");
        showStartView();
    }
    public void showStartView() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/startView.fxml"));
        mainlayout = loader.load();
        Scene scene = new Scene(mainlayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void goToView(String path) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(path));
        BorderPane homeBorderPane = loader.load();
        mainlayout.setCenter(homeBorderPane);
    }
    public static void main(String[] args) throws ClassNotFoundException {
        Application.launch(args);

    }
}

