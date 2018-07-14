package com.polchlopek.praca.magisterska.config;

import com.polchlopek.praca.magisterska.DAO.*;
import com.polchlopek.praca.magisterska.DTO.notedata.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginPanel.fxml"));
        primaryStage.setTitle("Pomiary");
        primaryStage.setScene(new Scene(root, 900, 600));
//        primaryStage.setMaxWidth(1000);
//        primaryStage.setMaxHeight(700);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        Image image = new Image("/image/icon.png");
        primaryStage.getIcons().add(image);
        primaryStage.show();

        // test DAO object
//        PersonDAO per = new PersonDAO();
//        System.out.println(per.getPerson("damian"));
//
//        LoginDAO log = new LoginDAO();
//        System.out.println("login: " + log.getLogins());
//
//        MeasurementDAO meas = new MeasurementDAO();
//        System.out.println("meas: " + meas.getMeasurements());
//        System.out.println("meas: " + meas.getMeasurement(1));
//
//        MeasurementDataDAO arr = new MeasurementDataDAO();
//        System.out.println("arr: " + arr.getDataMeassurement(1));
//
//        MeasurementCategoryDAO cat = new MeasurementCategoryDAO();
//        System.out.println("cat: " + cat.getArrayMeassurement(1));

    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void stop() throws Exception {
        try{
            NoteData.getInstance().storeNoteItems();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void init() throws Exception {
        try{
            NoteData.getInstance().loadNoteItems();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }
}
