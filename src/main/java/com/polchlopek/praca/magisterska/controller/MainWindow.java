package com.polchlopek.praca.magisterska.controller;

import com.polchlopek.praca.magisterska.DAO.MeasurementCategoryDAO;
import com.polchlopek.praca.magisterska.DTO.LoggedInUser;
import com.polchlopek.praca.magisterska.DTO.ReceivedDataFromFile;
import com.polchlopek.praca.magisterska.config.Main;
import com.polchlopek.praca.magisterska.entity.Measurement;
import com.polchlopek.praca.magisterska.entity.MeasurementCategory;
import com.polchlopek.praca.magisterska.entity.MeasurementData;
import com.polchlopek.praca.magisterska.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainWindow {

    @FXML
    private BorderPane mainBorderPane;


    @FXML
    public void showTimeGraph() throws IOException {
        Node root_time = FXMLLoader.load(getClass().getResource("/view/Graphs.fxml"));
        mainBorderPane.setCenter(root_time);
    }

    @FXML
    public void showNotes() throws IOException {
        Node root_notes = FXMLLoader.load(getClass().getResource("/view/centerNotes.fxml"));
        mainBorderPane.setCenter(root_notes);
    }

    @FXML
    public void showSTMCommunication() throws IOException {
        Node root_notes = FXMLLoader.load(getClass().getResource("/view/communication.fxml"));
        mainBorderPane.setCenter(root_notes);
    }

    @FXML
    public void showDatabase() throws IOException {
        Node root_notes = FXMLLoader.load(getClass().getResource("/view/database.fxml"));
        mainBorderPane.setCenter(root_notes);
    }

    @FXML
    public void logout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginPanel.fxml"));
        Main.getPrimaryStage().setScene(new Scene(root,900, 600));
    }

    @FXML
    public void saveDiagrams() {
        System.out.println("Zapisuje");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Signal");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All File", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );

        File file = fileChooser.showSaveDialog(mainBorderPane.getScene().getWindow());

        if(file != null){
            try {
                saveFile("123456", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void saveFile(String content, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();

        System.out.println("Metoda zapisujaca plik !!!");

    }

    @FXML
    public void openDiagrams(){
        System.out.println("Open");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All File", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );

        File selectedFile = fileChooser.showOpenDialog(mainBorderPane.getScene().getWindow());

        try {
            openFile(selectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openFile(File file) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        byte[] dataFromFile = new byte[(int) file.length()];
        fis.read(dataFromFile);
        fis.close();
        String contentFile = new String(dataFromFile, "UTF-8");

        final String description_pattern = "Description: ([\\w|\\W]+)" +
                "Category: ([\\w|\\W]+)" +
                "Description axis x: ([\\w|\\W]+)" +
                "Description axis y: ([\\w|\\W]+)" +
                "Data:([\\w|\\W]+)";

        final String data_pattern = "(-?\\d*.?\\d+), (-?\\d*.?\\d+)";

        Pattern r = Pattern.compile(description_pattern);
        Matcher m = r.matcher(contentFile);

        String description;
        String category;
        String descriptionAxisX;
        String descriptionAxisY;
        String data;

        if (m.find()){
            try{
                description = m.group(1).trim();
                category = m.group(2).trim();
                descriptionAxisX = m.group(3).trim();
                descriptionAxisY = m.group(4).trim();
                data = m.group(5);
            }
            catch(Exception e){
                System.out.println("Blad podczas parsowania pliku !!!");
                return;
            }
        }
        else {
            System.out.println("Zly format pliku !!!");
            return;
        }

        java.util.Date utilDate = new java.util.Date();
        Date sqlDate = new Date(utilDate.getTime());
        Measurement measurementToAdd = new Measurement(sqlDate, description);

        Pattern rData = Pattern.compile(data_pattern);
        Matcher mData = rData.matcher(data);

        // dodanie wektora danych
        while (mData.find()) {
            System.out.println(mData.group(1) + ", " + mData.group(2));
            measurementToAdd.addNode(new MeasurementData(Float.parseFloat(mData.group(1)),
                    Float.parseFloat(mData.group(2))));
        }

        MeasurementCategoryDAO measurementCategoryDAO = new MeasurementCategoryDAO();
        MeasurementCategory measurementCategory = measurementCategoryDAO.getMeasurementCategory(category);
        if (measurementCategory == null) {
            measurementCategory = new MeasurementCategory(category,
                    descriptionAxisX, descriptionAxisY);
        }
        measurementToAdd.setCategory(measurementCategory);

        // ustawienie osoby
        User person = LoggedInUser.getInstance().getLoggedInUSer();
        measurementToAdd.setUserID(person);

        ReceivedDataFromFile.getInstance().setMeasurement(measurementToAdd);
    }

    @FXML
    public void showAboutTimeDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Signal");
        dialog.setHeaderText("In this window you can see all important information about signal");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/helpDialog.fxml"));

        if (loadContentToScreen(dialog, fxmlLoader)) return;

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Help helpController = fxmlLoader.getController();
        helpController.setHelpLabel("W aplikacji zostały dodane dwa typy wykresów: liniowy i słupkowy. \n" +
                "Po zmianie odpowiedzniej zakładki w widoku odpowiedzialnym a wyświetlanie wykresów \n" +
                "ukazują się wykresy wyświetlające ostatnio otrzymane pomiary z mikrokontrolera.");
        dialog.showAndWait();
    }

    @FXML
    public void showDatabaseDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Database");
        dialog.setHeaderText("In this window you can see all important information about database");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/helpDialog.fxml"));

        if (loadContentToScreen(dialog, fxmlLoader)) return;

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Help helpController = fxmlLoader.getController();
        helpController.setHelpLabel("Za pomocą bazy danych użytkownik może zarządzać wybranymi przez siebie pomiarami. \n" +
                "------------------------------------------------------------------------------- \n" +
                "Dostępne operacje na bazie danych to: \n" +
                "1. Wyświetlanie i usuwanie danych o zarejestrowanych użytkownikach. \n" +
                "2. Wyświetlanie danych o logowaniach uzytkowników do aplikacji. \n" +
                "3. Wyświetlanie, dodawanie i usuwanie pomiarow. \n" +
                "4. Wyświetlanie, dodawnaie i usuwanie kategorii pomiarow. \n");
        dialog.showAndWait();
    }

    @FXML
    public void showCommunicationDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Communication");
        dialog.setHeaderText("In this window you can see all important information about communication between apllication and STM32");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/helpDialog.fxml"));

        if (loadContentToScreen(dialog, fxmlLoader)) return;

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Help helpController = fxmlLoader.getController();
        helpController.setHelpLabel("W widoku odpowiezialnym za kommunikacje z mikrokontrolerem mamy możliwość: \n" +
                "1. Kommunikacji z mikro kontrolerem. \n" +
                "2. Wybrania algorytmu, jaki chcemy użyć do przetworzenia, zmierzonego sygnału. \n" +
                "3. Ustawienia liczby próbek jakie chcemy zmierzyć. \n" +
                "4. Rozpoczęcia sekwencji pomiarowej.");
        dialog.showAndWait();
    }


    @FXML
    public void showAboutNoteDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Help Note");
        dialog.setHeaderText("In this window you can see all important information about note");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/helpDialog.fxml"));

        if (loadContentToScreen(dialog, fxmlLoader)) return;

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Help helpController = fxmlLoader.getController();
        helpController.setHelpLabel("W aplikacji jest możliwość dodawania, edytowania i usuwania notatek.");
        dialog.showAndWait();
    }

    private boolean loadContentToScreen(Dialog<ButtonType> dialog, FXMLLoader fxmlLoader) {
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return true;
        }
        return false;
    }

}
