package com.polchlopek.praca.magisterska.config;

import com.polchlopek.praca.magisterska.controller.STMCommunication;
import com.polchlopek.praca.magisterska.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginPanel.fxml"));
        primaryStage.setTitle("Pomiary");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(700);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        Image image = new Image("/image/icon.png");
        primaryStage.getIcons().add(image);
        primaryStage.show();



//        VBox vBox = new VBox();
//
//        StackPane root = new StackPane();
//        root.getChildren().add(vBox);
//
//        Scene scene = new Scene(root, 300, 250);
//
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);


    }


    @Override
    public void stop() throws Exception {

    }

    @Override
    public void init() throws Exception {

    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }
}
